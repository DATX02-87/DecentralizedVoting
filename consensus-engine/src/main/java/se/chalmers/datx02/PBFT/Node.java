package se.chalmers.datx02.PBFT;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.PbftMessageInfo;
import pbft.sdk.protobuf.PbftNewView;
import pbft.sdk.protobuf.PbftSeal;
import pbft.sdk.protobuf.PbftSignedVote;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusPeerInfo;
import se.chalmers.datx02.PBFT.lib.exceptions.FaultyPrimary;
import se.chalmers.datx02.PBFT.lib.exceptions.InternalError;
import se.chalmers.datx02.PBFT.lib.exceptions.InvalidMessage;
import se.chalmers.datx02.PBFT.lib.exceptions.ServiceError;
import se.chalmers.datx02.PBFT.lib.timing.Timeout;
import se.chalmers.datx02.PBFT.message.MessageType;
import se.chalmers.datx02.PBFT.message.ParsedMessage;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.exceptions.InvalidStateException;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static se.chalmers.datx02.PBFT.message.MessageType.PrePrepare;

public class Node {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Service service;
    private MessageLog msg_log;

    public Node(Config config, ConsensusBlock chainHead, List<ConsensusPeerInfo> connected_peers, Service service, State state){
        this.service = service;
        this.msg_log = new MessageLog(config);

        msg_log.addValidatedBlock(chainHead);
        state.setChainHead(chainHead.getBlockId().toByteArray());

        if(chainHead.getBlockNum() > 1){
            try {
                // If starting up with a block that has a consensus seal, update the view to match
                PbftSeal seal = PbftSeal.parseFrom(chainHead.getPayload());
                state.setView(seal.getInfo().getView());
                logger.info(String.format("Updated view to %d on startup", state.getView()));
            } catch (InvalidProtocolBufferException e) {
                // ignore
            }

            // If connected to any peers already, send bootstrap commit messages to them
            for(ConsensusPeerInfo peer : connected_peers){
                broadcastBootstrapCommit(peer.getPeerId().toByteArray(), state);
                // TODO: Catch exception
            }
        }

        if(state.isPrimary()){
            try {
                this.service.initializeBlock(null);
            } catch (InvalidStateException | UnknownBlockException | ReceiveErrorException e) {
                logger.error(String.format("Couldnt initialize block on startup due to error: %s", e));
            }
        }
    }

    public void onPeerMessage(ParsedMessage msg, State state) throws InvalidMessage {
        logger.trace(state + ": Got peer message: " + msg.info());

        if(state.getMembers().contains(msg.info().getSignerId().toByteArray())){
            throw new InvalidMessage("Received message from node (" + HexBin.encode(msg.info().getSignerId().toByteArray())
                    + ") that is not a member of the PBFT network");
        }

        MessageType msg_type = MessageType.from(msg.info().getMsgType());

        if(state.getMode() == State.Mode.ViewChanging
                && msg_type != MessageType.NewView
                && msg_type != MessageType.ViewChange){
            logger.debug(state + ": Node is view changing: ignoring " + msg_type + " message");
            return;
        }

        try {
            switch (msg_type) {
                case PrePrepare:
                    handlePrePrepare(msg, state);
                    break;
                case Prepare:
                    handlePrepare(msg, state);
                    break;
                case Commit:
                    handleCommit(msg, state);
                    break;
                case ViewChange:
                    handleViewChange(msg, state);
                    break;
                case NewView:
                    handleNewView(msg, state);
                    break;
                case SealRequest:
                    handleSealRequest(msg, state);
                    break;
                case Seal:
                    handleSealResponse(msg, state);
                    break;
                default:
                    logger.warn("Received message with unknown type: " + msg_type);
                    break;
            }
        }
        catch(Exception e){
            logger.error(String.format("Node failed to handle a message due to error: %s", e));
        }
    }

    public void handlePrePrepare(ParsedMessage msg, State state) throws InvalidMessage, FaultyPrimary {
        if(msg.info().getSignerId().toByteArray() != state.getPrimaryId()){
            logger.warn(String.format("Got PrePrepare from a secondary node {%s}; ignoring message",
                    msg.info().getSignerId().toByteArray()));
            return;
        }

        if(msg.info().getView() != state.getView()){
            throw new InvalidMessage(String.format("Node is on view {%d}, but a PrePrepare for view {%d} was received",
                    state.getView(),
                    msg.info().getView()));
        }

        // Check that no `PrePrepare`s already exist with this view and sequence number but a
        // different block; if this is violated, the primary is faulty so initiate a view change
        List<ParsedMessage> messages = msg_log.getMessageOfTypeSeqView(PrePrepare, msg.info().getSeqNum(), msg.info().getView());
        List<ParsedMessage> messagesFiltered = messages.stream().filter(msgF -> msgF.getBlockId().toByteArray() == msg.getBlockId().toByteArray()).collect(Collectors.toList());

        if(!messagesFiltered.isEmpty()){
            startViewChange(state.getView() + 1, state);

            // TODO: throw exception if above function fails(?)
            throw new FaultyPrimary(String.format("When checking PrePrepare with block {%s}, found PrePrepare(s) with same view and seq num but mismatched block(s): {%s}",
                    HexBin.encode(msg.getBlockId().toByteArray()),
                    messagesFiltered));
        }

        msg_log.addMessage(msg);

        tryPreparing(msg.getBlockId().toByteArray(), state);
    }

    public void handlePrepare(ParsedMessage msg, State state) throws InvalidMessage, FaultyPrimary, InternalError {
        PbftMessageInfo info = msg.info();
        byte[] block_id = msg.getBlockId().toByteArray();

        if(msg.info().getView() != state.getView()){
            throw new InvalidMessage(String.format("Node is on view {%d}, but a Prepare for view {%d} was received",
                    state.getView(),
                    msg.info().getView()));
        }

        if(info.getSignerId().toByteArray() == state.getPrimaryId()){
            startViewChange(state.getView() + 1, state);

            // TODO: throw exception if above function fails(?) (In rust: ?;)
            throw new FaultyPrimary(String.format("Received Prepare from primary at view {%d}, seq_num {%d}",
                    state.getView(),
                    state.getSeqNum()));
        }

        msg_log.addMessage(msg);

        if(info.getSeqNum() == state.getSeqNum()
                && state.getPhase() == State.Phase.Preparing){
            boolean has_matching_pre_prepare = msg_log.hashPrePrepare(info.getSeqNum(), info.getView(), block_id);
            boolean has_required_prepares = (
                    msg_log.getMessageOfTypeSeqViewBlock(MessageType.Prepare, info.getSeqNum(), info.getView(), block_id)
                    .size() > 2 * state.getFaultyNods()
            );

            if(has_matching_pre_prepare && has_required_prepares){
                state.switchPhase(State.Phase.Commiting);
                broadcastPBFTMessage(state.getView(), state.getSeqNum(), MessageType.Commit, block_id, state);
            }
        }
    }

    public void handleCommit(ParsedMessage msg, State state) throws InvalidMessage, ServiceError {
        PbftMessageInfo info = msg.info();
        byte[] block_id = msg.getBlockId().toByteArray();

        if(msg.info().getView() != state.getView()){
            throw new InvalidMessage(String.format("Node is on view {%d}, but a Commit for view {%d} was received",
                    state.getView(),
                    msg.info().getView()));
        }

        msg_log.addMessage(msg);


        if(info.getSeqNum() == state.getSeqNum()
                && state.getPhase() == State.Phase.Commiting){
            boolean has_matching_pre_prepare = msg_log.hashPrePrepare(info.getSeqNum(), info.getView(), block_id);
            boolean has_required_commits = (
                    msg_log.getMessageOfTypeSeqViewBlock(MessageType.Commit, info.getSeqNum(), info.getView(), block_id)
                            .size() > 2 * state.getFaultyNods()
            );

            if(has_matching_pre_prepare && has_required_commits){
                try {
                    service.commitBlock(block_id);
                } catch (UnknownBlockException | ReceiveErrorException e) {
                    throw new ServiceError(String.format("Failed to commit block {%s}", HexBin.encode(block_id)));
                }

                state.setAndCreateFinishing(false);
                state.commit_timeout.stop();
            }
        }
    }

    public void handleViewChange(ParsedMessage msg, State state){
        long msg_view = msg.info().getView();

        if(msg_view <= state.getView()
            || state.getMode() == State.Mode.ViewChanging
                && msg_view < state.getMode().getViewChanging()){
            logger.debug(String.format("Ignoring stale view change message for view {%d}", msg_view));
            return;
        }

        msg_log.addMessage(msg);

        boolean is_later_view = (state.getMode() == State.Mode.Normal
                || state.getMode() == State.Mode.ViewChanging
                && msg_view > state.getMode().getViewChanging());

        boolean start_view_change = (msg_log.getMessageOfTypeView(MessageType.ViewChange, msg_view).size() > state.getFaultyNods());

        if(is_later_view && start_view_change){
            logger.info(String.format("%s Received f + 1 ViewChange messages; starting early view change", state));
            startViewChange(msg_view, state);
            return;
        }

        List<ParsedMessage> messages = msg_log.getMessageOfTypeView(MessageType.ViewChange, msg_view);

        // If there are 2f + 1 ViewChange messages and the view change timeout is not already
        // started, update the timeout and start it
        if(!state.view_change_timeout.isActive() && messages.size() > state.getFaultyNods()*2){
            state.view_change_timeout = new Timeout(state.view_change_duration.multipliedBy(msg_view - state.getView()));
            state.view_change_timeout.start();
        }

        List<ParsedMessage> messages_from_other_nodes = messages.stream().filter(x -> !x.fromSelf()).collect(Collectors.toList());

        if(state.isPrimaryAtView(msg_view)
                && messages_from_other_nodes.size() >= 2 * state.getFaultyNods()){
            PbftNewView.Builder new_viewBuilder = PbftNewView.newBuilder()
                    .setInfo(
                            PbftMessageInfo.newBuilder()
                                    .setSignerId(ByteString.copyFrom(state.getPeerId()))
                                    .setSeqNum(state.getSeqNum() - 1)
                                    .setView(msg_view)
                                    .setMsgType("NewView")
                                    .build()
                    );

            // Add votes
            // TODO: Double check if this is correct
            int i = 0;
            for(PbftSignedVote vote : signedVotesFromMessages(messages_from_other_nodes)){
                new_viewBuilder.setViewChanges(i++, vote);
            }

            PbftNewView new_view = new_viewBuilder.build();

            logger.trace(String.format("Created NewView message {%s}", new_view));

            broadcastMessage(new ParsedMessage(new_view), state);
        }
    }

    public void handleNewView(ParsedMessage msg, State state) throws InternalError, ServiceError {
        PbftNewView new_view = msg.getNewViewMessage();

        // TODO: Will throw exception, fix
        verifyNewView(new_view, state);

        if(state.isPrimary()){
            try {
                service.cancelBlock();
            } catch (InvalidStateException | ReceiveErrorException e) {
                logger.info(String.format("Failed to cancel block when becoming secondary: %s", e));
            }
        }

        state.setView(new_view.getInfo().getView());
        state.view_change_timeout.stop();

        logger.info(String.format("%s: Updated to view %d", state, state.getView()));

        state.setModeNormal();
        if(state.getPhase() != State.Phase.Finishing){
            state.switchPhase(State.Phase.PrePreparing);
        }
        state.idle_teamout.start();

        if(state.isPrimary()){
            try {
                service.initializeBlock(null);
            } catch (InvalidStateException | UnknownBlockException | ReceiveErrorException e) {
                throw new ServiceError(String.format("Couldn't initialize block after view change, error: %s", e));
            }
        }
    }

    public void handleSealRequest(ParsedMessage msg, State state){
        if(state.getSeqNum() == msg.info().getSeqNum() + 1){
            sendSealResponse(msg.info().getSignerId().toByteArray(), state);
        }
        else if(state.getSeqNum() == msg.info().getSeqNum()){
            msg_log.addMessage(msg);
        }
    }

    public void handleSealResponse(ParsedMessage msg, State state){
        PbftSeal seal = msg.getSeal();

        try {
            state.switchPhase(State.Phase.Finishing);
            // Todo: check other switchphases above to seei f they follow the rules
            return;
        } catch (InternalError internalError) {
            // ignore
        }

        byte[] previous_id = msg_log.getBlockWithId(seal.getBlockId().toByteArray()).toByteArray();
        // TODO: fix previous_id

        verifyConsensusSeal(seal, previous_id, state);
        // TODO: Will throw exception, fix

        catchup(false, seal, state);
    }

    public void onBlockNew(ConsensusBlock block, State state){

    }

    public void onBlockValid(byte[] blockId, State state){

    }

    public void onBlockInvalid(byte[] blockId){

    }

    public void tryHandlingBlock(ConsensusBlock block, State state){

    }

    public void catchup(boolean catchup_again, PbftSeal seal, State state){

    }

    public void onBlockCommit(byte[] blockId, State state){

    }

    public void updateMembership(byte[] blockId, State state){

    }

    public void tryPreparing(byte[] blockId, State state){

    }

    public void onPeerConnected(byte[] peerId, State state){

    }

    public void broadcastBootstrapCommit(byte[] peerId, State state){

    }

    public List<PbftSignedVote> signedVotesFromMessages(List<ParsedMessage> msgs){
        return null;
    }

    public PbftSeal buildSeal(State state){
        return null;
    }

    public <T,R> byte[] verifyVote(PbftSignedVote vote, MessageType expected_type, Function<T,R> func){
        return null;
    }

    public void verifyNewView(PbftNewView view, State state){

    }

    public PbftSeal verifyConsensusSealFromBlock(ConsensusBlock block, State state){
        return null;
    }

    public void verifyConsensusSeal(PbftSeal seal, byte[] blockId, State state){

    }

    public void tryPublish(State state){

    }

    public boolean checkIdleTimeoutExpired(State state){
        return true;
    }

    public void startIdleTimeout(State state){

    }

    public boolean checkCommitTimeoutExpired(State state){
        return true;
    }

    public void startCommitTimeout(State state){
    }

    public boolean checkViewChangeTimeoutExpired(State state){
        return true;
    }

    public void broadcastPBFTMessage(long view, long seq_num, MessageType msg_type, byte[] blockId, State state){

    }

    public void broadcastMessage(ParsedMessage msg, State state){

    }

    public void sendSealResponse(byte[] recipient, State state){

    }

    public void sendSealResponse(long view, State state){

    }

    public void startViewChange(long view ,State state){

    }
}
