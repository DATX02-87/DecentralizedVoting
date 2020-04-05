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
import sawtooth.sdk.protobuf.Consensus;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusPeerInfo;
import se.chalmers.datx02.PBFT.lib.exceptions.*;
import se.chalmers.datx02.PBFT.lib.exceptions.InternalError;
import se.chalmers.datx02.PBFT.lib.timing.RetryUntilOk;
import se.chalmers.datx02.PBFT.lib.timing.Timeout;
import se.chalmers.datx02.PBFT.message.MessageType;
import se.chalmers.datx02.PBFT.message.ParsedMessage;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.exceptions.InvalidStateException;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static se.chalmers.datx02.PBFT.message.MessageType.*;

public class Node {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Service service;
    private State state;
    private MessageLog msg_log;

    public Node(Config config, ConsensusBlock chainHead, List<ConsensusPeerInfo> connected_peers, Service service, State state){
        this.service = service;
        this.msg_log = new MessageLog(config);
        this.state = state;

        msg_log.addValidatedBlock(chainHead);
        this.state.setChainHead(chainHead.getBlockId().toByteArray());

        if(chainHead.getBlockNum() > 1){
            try {
                // If starting up with a block that has a consensus seal, update the view to match
                PbftSeal seal = PbftSeal.parseFrom(chainHead.getPayload());
                this.state.setView(seal.getInfo().getView());
                logger.info(String.format("Updated view to %d on startup", this.state.getView()));
            } catch (InvalidProtocolBufferException e) {
                // ignore
            }

            // If connected to any peers already, send bootstrap commit messages to them
            for(ConsensusPeerInfo peer : connected_peers){
                broadcastBootstrapCommit(peer.getPeerId().toByteArray());
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

    public void onPeerMessage(ParsedMessage msg) throws InvalidMessage {
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
                    handlePrePrepare(msg);
                    break;
                case Prepare:
                    handlePrepare(msg);
                    break;
                case Commit:
                    handleCommit(msg);
                    break;
                case ViewChange:
                    handleViewChange(msg);
                    break;
                case NewView:
                    handleNewView(msg);
                    break;
                case SealRequest:
                    handleSealRequest(msg);
                    break;
                case Seal:
                    handleSealResponse(msg);
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

    public void handlePrePrepare(ParsedMessage msg) throws InvalidMessage, FaultyPrimary {
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
            startViewChange(state.getView() + 1);

            // TODO: throw exception if above function fails(?)
            throw new FaultyPrimary(String.format("When checking PrePrepare with block {%s}, found PrePrepare(s) with same view and seq num but mismatched block(s): {%s}",
                    HexBin.encode(msg.getBlockId().toByteArray()),
                    messagesFiltered));
        }

        msg_log.addMessage(msg);

        tryPreparing(msg.getBlockId().toByteArray());
    }

    public void handlePrepare(ParsedMessage msg) throws InvalidMessage, FaultyPrimary, InternalError {
        PbftMessageInfo info = msg.info();
        byte[] block_id = msg.getBlockId().toByteArray();

        if(msg.info().getView() != state.getView()){
            throw new InvalidMessage(String.format("Node is on view {%d}, but a Prepare for view {%d} was received",
                    state.getView(),
                    msg.info().getView()));
        }

        if(info.getSignerId().toByteArray() == state.getPrimaryId()){
            startViewChange(state.getView() + 1);

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
                broadcastPBFTMessage(state.getView(), state.getSeqNum(), MessageType.Commit, block_id);
            }
        }
    }

    public void handleCommit(ParsedMessage msg) throws InvalidMessage, ServiceError {
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

    public void handleViewChange(ParsedMessage msg){
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
            startViewChange(msg_view);
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

            broadcastMessage(new ParsedMessage(new_view));
        }
    }

    public void handleNewView(ParsedMessage msg) throws InternalError, ServiceError {
        PbftNewView new_view = msg.getNewViewMessage();

        // TODO: Will throw exception, fix
        verifyNewView(new_view);

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

    public void handleSealRequest(ParsedMessage msg){
        if(state.getSeqNum() == msg.info().getSeqNum() + 1){
            sendSealResponse(msg.info().getSignerId().toByteArray());
        }
        else if(state.getSeqNum() == msg.info().getSeqNum()){
            msg_log.addMessage(msg);
        }
    }

    public void handleSealResponse(ParsedMessage msg){
        PbftSeal seal = msg.getSeal();

        try {
            state.switchPhase(State.Phase.Finishing);
            // Todo: check other switchphases above to seei f they follow the rules
            return;
        } catch (InternalError internalError) {
            logger.error(String.format("Failed to switch phase on handleSealResponse"));
        }

        byte[] previous_id = msg_log.getBlockWithId(seal.getBlockId().toByteArray()).toByteArray();
        // TODO: fix previous_id

        verifyConsensusSeal(seal, previous_id);
        // TODO: Will throw exception, fix

        try {
            catchup(false, seal);
        } catch (ServiceError e) {
            logger.error(String.format("Failed to catchup due: %s", e));
        }
    }

    public void onBlockNew(ConsensusBlock block) throws InternalError, ServiceError {
        logger.info(String.format("%s: Got BlockNew: %d / %s",
                state,
                block.getBlockNum(),
                HexBin.encode(block.getBlockId().toByteArray())));

        logger.trace(String.format("Block details: %s", block));

        // Only future blocks should be considered since committed blocks are final
        if(block.getBlockNum() < state.getSeqNum()){
            try {
                service.failBlock(block.getBlockId().toByteArray());
            } catch (UnknownBlockException | ReceiveErrorException e) {
                logger.error(String.format("Couldn't fail block due to error: %s", e));
            }

            throw new InternalError(String.format("Received block {%d} / {%s} that is older than the current sequence number: {%d}",
                    block.getBlockNum(),
                    HexBin.encode(block.getBlockId().toByteArray()),
                    state.getSeqNum()));
        }

        // Make sure the node already has the previous block, since the consensus seal can't be
        // verified without it
        ConsensusBlock previous_block = msg_log.getBlockWithId(block.getPreviousId().toByteArray());
        if(previous_block == null)
            previous_block = msg_log.getUnvalidatedBlockWithId(block.getPreviousId().toByteArray());
        if(previous_block == null){
            try {
                service.failBlock(block.getBlockId().toByteArray());
            } catch (UnknownBlockException | ReceiveErrorException e) {
                logger.error(String.format("Couldn't fail block due to error: %s", e));
            }

            throw new InternalError(String.format("Received block {%d} / {%s} but node does not have previous block: {%s}",
                    block.getBlockNum(),
                    HexBin.encode(block.getBlockId().toByteArray()),
                    HexBin.encode(block.getPreviousId().toByteArray())));
        }

        // Make sure that the previous block has the previous block number (enforces that blocks
        // are strictly monotically increasing by 1)
        if(previous_block.getBlockNum() != block.getBlockNum() - 1){
            try {
                service.failBlock(block.getBlockId().toByteArray());
            } catch (UnknownBlockException | ReceiveErrorException e) {
                logger.error(String.format("Couldn't fail block due to error: %s", e));
            }

            throw new InternalError(String.format("Received block {%d} / {%s} but its previous block {%d} / {%s} " +
                            "does not have the previous block_num",
                    block.getBlockNum(),
                    HexBin.encode(block.getBlockId().toByteArray()),
                    block.getBlockNum() - 1,
                    HexBin.encode(block.getPreviousId().toByteArray())));
        }

        msg_log.addUnvalidatedBlock(block);

        // Have the validator check the block
        List<byte[]> block_check = new ArrayList<>();
        block_check.add(block.getBlockId().toByteArray());

        try {
            service.checkBlocks(block_check);
        } catch (UnknownBlockException | ReceiveErrorException e) {
            throw new ServiceError(String.format("Failed to check block {%d} / {%s} ",
                    block.getBlockNum(),
                    HexBin.encode(block.getBlockId().toByteArray())));
        }
    }

    public void onBlockValid(byte[] blockId) throws InvalidMessage {
        logger.info(String.format("Got blockvalid: %s",
                HexBin.encode(blockId)));

        ConsensusBlock block = msg_log.blockValidated(blockId);
        if(block == null){
            throw new InvalidMessage(String.format("Received BlockValid message for an unknown block: {%s}",
                    HexBin.encode(blockId)));
        }

        tryHandlingBlock(block);
    }

    public void onBlockInvalid(byte[] blockId) throws InvalidMessage {
        logger.info(String.format("Got BlockInvalid: %s", HexBin.encode(blockId)));

        if(!msg_log.blockInvalidated(blockId)){
            throw new InvalidMessage(String.format("Received BlockInvalid message for an unknown block: %s",
                    HexBin.encode(blockId)));
        }

        try {
            service.failBlock(blockId);
        } catch (UnknownBlockException | ReceiveErrorException e) {
            logger.error(String.format("Couldn't fail block due to error: %s", e));
        }
    }

    public boolean tryHandlingBlock(ConsensusBlock block){
        if(block.getBlockNum() > state.getSeqNum() + 1)
            return true;

        PbftSeal seal = verifyConsensusSealFromBlock(block);
        // TODO: Will throw exception, fix

        boolean is_waiting = (state.getPhase() == State.Phase.Finishing);

        if(block.getBlockNum() > state.getSeqNum() && !is_waiting){
            try {
                catchup(true, seal);
            } catch (ServiceError e) {
                logger.error(String.format("Failed to catchup due: %s", e));
                return false;
            }
        }
        else if(block.getBlockNum() == state.getSeqNum()){
            if(block.getSignerId().toByteArray() == state.getPeerId() && state.isPrimary()){
                logger.info("Broadcasting PrePrepares");
                broadcastPBFTMessage(state.getView(),
                        state.getSeqNum(),
                        PrePrepare,
                        block.getBlockId().toByteArray());
            }
            else{
                tryPreparing(block.getBlockId().toByteArray());
            }
        }

        return true;
    }

    public void catchup(boolean catchup_again, PbftSeal seal) throws ServiceError {
        logger.info(String.format("%s: Attempting to commit block %d using catch-up",
                state,
                state.getSeqNum()));

        List<ParsedMessage> messages = new ArrayList<>();

        for(PbftSignedVote vote : seal.getCommitVotesList()){
            try {
                messages.add(new ParsedMessage(vote));
            } catch (SerializationError serializationError) {
                // Todo: double check try_fold method in rust
                break;
            }
        }

        long view = messages.get(0).info().getView();
        if(view != state.getView()){
            logger.info(String.format("Updating view from {%d} to {%d}",
                    state.getView(),
                    view));

            state.setView(view);
        }

        for(ParsedMessage message : messages){
            msg_log.addMessage(message);
        }

        try {
            service.commitBlock(seal.getBlockId().toByteArray());
        } catch (UnknownBlockException | ReceiveErrorException e) {
            throw new ServiceError(String.format("Failed to commit block with catch-up {%d} / {%s}",
                    state.getSeqNum(),
                    HexBin.encode(seal.getBlockId().toByteArray())));
        }

        state.idle_teamout.stop();
        state.setAndCreateFinishing(catchup_again);
    }

    public void onBlockCommit(byte[] blockId) throws ServiceError {
        logger.info(String.format("%s: Got BlockCommit for {%s}", state, HexBin.encode(blockId)));

        boolean is_catching_up = (state.getPhase().getFinishing()
                && state.getPhase() == State.Phase.Finishing);

        List<byte[]> invalid_block_ids = msg_log.getBlocksWithNum(state.getSeqNum())
                .stream().map(x -> {
                    if(x.getBlockId().toByteArray() != blockId)
                        return x.getBlockId().toByteArray();
                    else
                        return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());

        for(byte[] id : invalid_block_ids){
            try {
                service.failBlock(id);
            } catch (UnknownBlockException | ReceiveErrorException e) {
                logger.error(String.format("Couldn't fail block %s due to error %s", HexBin.encode(id),e));
            }
        }

        state.setSeqNum(state.getSeqNum() + 1);
        state.setModeNormal();
        try {
            state.switchPhase(State.Phase.PrePreparing);
        } catch (InternalError internalError) {
            logger.error(String.format("Failed to switch phase on onBlockCommit"));
        }
        state.setChainHead(blockId);

        List<byte[]> requesters = msg_log.getMessageOfTypeSeq(SealRequest, state.getSeqNum() - 1)
                .stream().map(req -> req.info().getSignerId().toByteArray()).collect(Collectors.toList());

        for(byte[] req : requesters){
            sendSealResponse(req);
            // TODO: Exception will be thrown, fix
        }

        updateMembership(blockId);

        if(state.atForcedViewChange())
            state.setView(state.getView() + 1);

        msg_log.garbageCollect(state.getSeqNum());

        List<ConsensusBlock> grandchildren = msg_log.getBlocksWithNum(state.getSeqNum() + 1);

        for(ConsensusBlock block : grandchildren){
            if(tryHandlingBlock(block)){
                return;
            }
        }

        if(is_catching_up){
            logger.info(String.format("%s: Requesting seal to finish catch-up to block %s",
                    state,
                    state.getSeqNum()));

            broadcastPBFTMessage(state.getView(),
                    state.getSeqNum(),
                    SealRequest,
                    null);// Todo: check if null can be an "empty" blockid

            return;
        }

        state.idle_teamout.start();

        List<byte[]> block_ids = msg_log.getBlocksWithNum(state.getSeqNum())
                .stream().map(block -> block.getBlockId().toByteArray())
                .collect(Collectors.toList());

        for(byte[] id : block_ids){
            tryPreparing(id);
        }

        if(state.isPrimary()){
            logger.info(String.format("%s: Initializing block on top of %s",
                    state,
                    HexBin.encode(blockId)));

            try {
                service.initializeBlock(blockId);
            } catch (InvalidStateException | ReceiveErrorException | UnknownBlockException e) {
                throw new ServiceError(String.format("Couldn't initialize block after commit, error: %s", e));
            }
        }
    }

    public void updateMembership(byte[] blockId){
        RetryUntilOk retryUntilOk = new RetryUntilOk(state.exponential_retry_base, state.exponential_retry_max);

        List<String> setttingsSearch = new ArrayList<>();
        setttingsSearch.add("sawtooth.consensus.pbft.members");
        Map<String, String> settings = null;

        while(true){
            try{
                settings = service.getSettings(blockId, setttingsSearch);
                break;
            }
            catch(Exception e){
                try {
                    Thread.sleep(retryUntilOk.getDelay());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                retryUntilOk.check();
            }
        }

        List<byte[]> on_chain_members = Config.getMembersFromSettings(settings);

        if(on_chain_members != state.getMembers()){
            logger.info(String.format("Updating membership: %s", on_chain_members));
            state.setMembers(on_chain_members);

            long faulty_nodes = (state.getMembers().size() - 1)/3;
            if(faulty_nodes == 0){
                logger.warn("This network no longer contains enough nodes to be fault tolerant");
            }

            state.setFaultyNodes(faulty_nodes);
        }
    }

    public void tryPreparing(byte[] blockId){
        ConsensusBlock block = msg_log.getBlockWithId(blockId);
        if(block != null){
            if(state.getPhase() == State.Phase.PrePreparing
                    && msg_log.hashPrePrepare(state.getSeqNum(), state.getView(), blockId)
                    && block.getBlockNum() == state.getSeqNum()){
                try {
                    state.switchPhase(State.Phase.Preparing);

                    state.idle_teamout.stop();

                    state.commit_timeout.start();

                    if(!state.isPrimary())
                        broadcastPBFTMessage(state.getView(),
                                state.getSeqNum(),
                                Prepare,
                                blockId);

                } catch (InternalError internalError) {
                    logger.error("Failed to switch phase on tryPreparing");
                }
            }
        }
    }

    public void onPeerConnected(byte[] peerId){
        if(!state.getMembers().contains(peerId)
                || state.getSeqNum() == 1)
            return;

        broadcastBootstrapCommit(peerId);
    }

    public void broadcastBootstrapCommit(byte[] peerId){

    }

    public List<PbftSignedVote> signedVotesFromMessages(List<ParsedMessage> msgs){
        return null;
    }

    public PbftSeal buildSeal(){
        return null;
    }

    public <T,R> byte[] verifyVote(PbftSignedVote vote, MessageType expected_type, Function<T,R> func){
        return null;
    }

    public void verifyNewView(PbftNewView view){

    }

    public PbftSeal verifyConsensusSealFromBlock(ConsensusBlock block){
        return null;
    }

    public void verifyConsensusSeal(PbftSeal seal, byte[] blockId){

    }

    public void tryPublish(){

    }

    public boolean checkIdleTimeoutExpired(){
        return true;
    }

    public void startIdleTimeout(){

    }

    public boolean checkCommitTimeoutExpired(){
        return true;
    }

    public void startCommitTimeout(){
    }

    public boolean checkViewChangeTimeoutExpired(){
        return true;
    }

    public void broadcastPBFTMessage(long view, long seq_num, MessageType msg_type, byte[] blockId){

    }

    public void broadcastMessage(ParsedMessage msg){

    }

    public void sendSealResponse(byte[] recipient){

    }

    public void sendSealResponse(long view){

    }

    public void startViewChange(long view){

    }
}
