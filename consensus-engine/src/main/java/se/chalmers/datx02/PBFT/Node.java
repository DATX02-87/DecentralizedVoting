package se.chalmers.datx02.PBFT;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.*;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusPeerInfo;
import sawtooth.sdk.protobuf.ConsensusPeerMessageHeader;
import sawtooth.sdk.signing.Context;
import sawtooth.sdk.signing.CryptoFactory;
import sawtooth.sdk.signing.Secp256k1PublicKey;
import se.chalmers.datx02.PBFT.lib.exceptions.*;
import se.chalmers.datx02.PBFT.lib.exceptions.InternalError;
import se.chalmers.datx02.PBFT.lib.timing.RetryUntilOk;
import se.chalmers.datx02.PBFT.lib.timing.Timeout;
import se.chalmers.datx02.PBFT.message.MessageExtension;
import se.chalmers.datx02.PBFT.message.MessageType;
import se.chalmers.datx02.PBFT.message.ParsedMessage;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.exceptions.BlockNotReadyException;
import se.chalmers.datx02.lib.exceptions.InvalidStateException;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;

import java.util.*;
import java.util.stream.Collectors;

import static se.chalmers.datx02.PBFT.lib.Hash.verifySha512;
import static se.chalmers.datx02.PBFT.message.MessageExtension.logMessage;
import static se.chalmers.datx02.PBFT.message.MessageType.*;

/*
todo:
IMPORTANT:
If anything fails, double check for rust syntax:
try_fold
?;
[..]

&mut

Also check for: References to lists (Create new when necessary to not manipulate data)
 */

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
                try {
                    broadcastBootstrapCommit(peer.getPeerId().toByteArray());
                } catch (InternalError | SerializationError e) {
                    logger.error("Failed to broadcast bootstrap commit due to error: " + e);
                }
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
        logger.trace(state + ": Got peer message: " + logMessage(msg.info()));

        boolean isFromMember = state.getMembers().parallelStream()
                .anyMatch(member -> Arrays.equals(member, msg.info().getSignerId().toByteArray()));
        if(!isFromMember){
            throw new InvalidMessage("Received message from node (" + HexBin.encode(msg.info().getSignerId().toByteArray())
                    + ") that is not a member of the PBFT network");
        }

        MessageType msg_type = MessageType.valueOf(msg.info().getMsgType());

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
            throw new RuntimeException(e);
        }
    }

    public void handlePrePrepare(ParsedMessage msg) throws InvalidMessage, FaultyPrimary {
        if(!Arrays.equals(msg.info().getSignerId().toByteArray(), state.getPrimaryId())) {
            logger.warn(String.format("Got PrePrepare from a secondary node {%s}; ignoring message",
                    Arrays.toString(msg.info().getSignerId().toByteArray())));
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
        List<ParsedMessage> messagesFiltered = messages.stream()
                .filter(msgF -> msgF.getBlockId().equals(msg.getBlockId()))
                .collect(Collectors.toList());

        if(!messagesFiltered.isEmpty()){
            startViewChange(state.getView() + 1);

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

        if(Arrays.equals(info.getSignerId().toByteArray(), state.getPrimaryId())){
            startViewChange(state.getView() + 1);

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
                    .size() > 2 * state.getFaultyNodes()
            );

            if(has_matching_pre_prepare && has_required_prepares){
                state.switchPhase(State.Phase.Commiting, false);
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
                            .size() > 2 * state.getFaultyNodes()
            );

            if(has_matching_pre_prepare && has_required_commits){
                try {
                    service.commitBlock(block_id);
                } catch (UnknownBlockException | ReceiveErrorException e) {
                    throw new ServiceError(String.format("Failed to commit block {%s}", HexBin.encode(block_id)));
                }

                try {
                    state.switchPhase(State.Phase.Finishing, false);
                } catch (InternalError e) {
                    logger.error("Failed to switch phase due: " + e);
                }
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

        boolean start_view_change = (msg_log.getMessageOfTypeView(MessageType.ViewChange, msg_view).size() > state.getFaultyNodes());

        if(is_later_view && start_view_change){
            logger.info(String.format("%s Received f + 1 ViewChange messages; starting early view change", state));
            startViewChange(msg_view);
            return;
        }

        List<ParsedMessage> messages = msg_log.getMessageOfTypeView(MessageType.ViewChange, msg_view);

        // If there are 2f + 1 ViewChange messages and the view change timeout is not already
        // started, update the timeout and start it
        if(!state.view_change_timeout.isActive() && messages.size() > state.getFaultyNodes()*2){
            state.view_change_timeout = new Timeout(state.view_change_duration.multipliedBy(msg_view - state.getView()));
            state.view_change_timeout.start();
        }

        List<ParsedMessage> messages_from_other_nodes = messages.stream().filter(x -> !x.fromSelf()).collect(Collectors.toList());

        if(state.isPrimaryAtView(msg_view)
                && messages_from_other_nodes.size() >= 2 * state.getFaultyNodes()){
            PbftNewView.Builder new_viewBuilder = PbftNewView.newBuilder()
                    .setInfo(
                            MessageExtension.newMessageInfo(NewView, msg_view, state.getSeqNum()-1, state.getPeerId())
                    );

            // Add votes
            int i = 0;
            for(PbftSignedVote vote : signedVotesFromMessages(messages_from_other_nodes)){
                new_viewBuilder.setViewChanges(i++, vote);
            }

            PbftNewView new_view = new_viewBuilder.build();

            logger.trace(String.format("Created NewView message {%s}", new_view));

            broadcastMessage(new ParsedMessage(new_view));
        }
    }

    public void handleNewView(ParsedMessage msg) throws InternalError, ServiceError, InvalidMessage {
        PbftNewView new_view = msg.getNewViewMessage();


        try {
            verifyNewView(new_view);
        } catch (InvalidMessage e) {
            throw new InvalidMessage("NewView failed verification - Error was: " + e);
        }

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
            state.switchPhase(State.Phase.PrePreparing, false);
        }
        state.idle_timeout.start();

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
            try {
                sendSealResponse(msg.info().getSignerId().toByteArray());
            } catch (InternalError e) {
                logger.error(String.format("Failed to sendSealResponse from handleSealRequest, due to: %s", e));
            }
        }
        else if(state.getSeqNum() == msg.info().getSeqNum()){
            msg_log.addMessage(msg);
        }
    }

    public void handleSealResponse(ParsedMessage msg) throws InvalidMessage {
        PbftSeal seal = msg.getSeal();

        try {
            state.switchPhase(State.Phase.Finishing, false);

            return;
        } catch (InternalError internalError) {
            logger.error("Failed to switch phase on handleSealResponse");
        }

        ConsensusBlock previous_idBlock = msg_log.getBlockWithId(seal.getBlockId().toByteArray());
        byte[] previous_id;
        if(previous_idBlock == null)
            throw new InvalidMessage(String.format("Received a seal for a block (%s) that the node does not have",
                    HexBin.encode(seal.getBlockId().toByteArray())));
        else{
            if(previous_idBlock.getBlockNum() != state.getSeqNum()){
                throw new InvalidMessage(String.format("Received a seal for block {%s}, but block_num does not match node's " +
                        "seq_num: {%d} != {%d}",
                        HexBin.encode(seal.getBlockId().toByteArray()),
                        previous_idBlock.getBlockNum(),
                        state.getSeqNum()
                        ));
            }
            else
                previous_id = previous_idBlock.getBlockId().toByteArray();
        }

        try {
            verifyConsensusSeal(seal, previous_id);
        } catch (InvalidMessage e) {
            throw new InvalidMessage("Consensus seal failed verification - Error was:" + e);
        }


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
                    HexBin.encode(block.getBlockId().toByteArray()).substring(0, 6),
                    HexBin.encode(block.getPreviousId().toByteArray()).substring(0, 6)));
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
                HexBin.encode(blockId).substring(0, 6)));

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

    public boolean tryHandlingBlock(ConsensusBlock block) throws InvalidMessage {
        if(block.getBlockNum() > state.getSeqNum() + 1)
            return true;

        PbftSeal seal;
        try {
            seal = verifyConsensusSealFromBlock(block);
        } catch (InvalidMessage | SerializationError | InternalError e) {
            try {
                service.failBlock(block.getBlockId().toByteArray());
            } catch (UnknownBlockException | ReceiveErrorException ex) {
                throw new RuntimeException("Couldn't fail block due to error", e);
            }
            throw new InvalidMessage("Consensus seal failed verification - Error was:" + e);
        }

        boolean is_waiting = (state.getPhase() == State.Phase.Finishing);

        if(block.getBlockNum() > state.getSeqNum() && !is_waiting){
            try {
                catchup(true, seal);
            } catch (ServiceError e) {
                logger.error(String.format("Failed to catchup due: %s", e));
                return false;
            }
        } else if(block.getBlockNum() == state.getSeqNum()){
            boolean blockSignedByMe = Arrays.equals(block.getSignerId().toByteArray(), state.getPeerId());
            if(blockSignedByMe && state.isPrimary()){
                logger.info("Broadcasting PrePrepares");
                broadcastPBFTMessage(state.getView(),
                        state.getSeqNum(),
                        PrePrepare,
                        block.getBlockId().toByteArray());
            } else{
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
                messages = null; // Failed
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

        state.idle_timeout.stop();
        try {
            state.switchPhase(State.Phase.Finishing, catchup_again);
        } catch (InternalError e) {
            logger.error("Failed to switch phase due: " + e);
        }
    }

    public void onBlockCommit(byte[] blockId) throws ServiceError {
        logger.info(String.format("%s: Got BlockCommit for {%s}", state, HexBin.encode(blockId).substring(0, 6)));

        boolean is_catching_up = (state.getPhase().getFinishing()
                && state.getPhase() == State.Phase.Finishing);

        List<byte[]> invalid_block_ids = msg_log.getBlocksWithNum(state.getSeqNum())
                .stream().map(x -> {
                    if(!Arrays.equals(x.getBlockId().toByteArray(), blockId))
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
            state.switchPhase(State.Phase.PrePreparing, false);
        } catch (InternalError internalError) {
            logger.error(String.format("Failed to switch phase on onBlockCommit"));
        }
        state.setChainHead(blockId);

        List<byte[]> requesters = msg_log.getMessageOfTypeSeq(SealRequest, state.getSeqNum() - 1)
                .stream().map(req -> req.info().getSignerId().toByteArray()).collect(Collectors.toList());

        for(byte[] req : requesters){
            try {
                sendSealResponse(req);
            } catch (InternalError e) {
                logger.error(String.format("Failed to send seal response due to: {%s}", e));
            }
        }

        updateMembership(blockId);

        if(state.atForcedViewChange())
            state.setView(state.getView() + 1);

        msg_log.garbageCollect(state.getSeqNum());

        List<ConsensusBlock> grandchildren = msg_log.getBlocksWithNum(state.getSeqNum() + 1);

        try{
            for(ConsensusBlock block : grandchildren){
                if(tryHandlingBlock(block))
                    return;
            }

            return; // return if succeded
        }
        catch (InvalidMessage invalidMessage) { } // ignore


        if(is_catching_up){
            logger.info(String.format("%s: Requesting seal to finish catch-up to block %s",
                    state,
                    state.getSeqNum()));

            broadcastPBFTMessage(state.getView(),
                    state.getSeqNum(),
                    SealRequest,
                    new byte[]{});

            return;
        }

        state.idle_timeout.start();

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

        Map<String, String> settings = retryUntilOk.run(() -> service.getSettings(blockId, Collections.singletonList("sawtooth.consensus.pbft.members")));


        List<byte[]> on_chain_members = Config.getMembersFromSettings(settings);

        boolean sameMembers = listOfByteArrsEquals(on_chain_members, state.getMembers());
        if (!sameMembers) {
            logger.info(String.format("Updating membership: %s", on_chain_members));
            state.setMembers(on_chain_members);

            long faulty_nodes = (state.getMembers().size() - 1)/3;
            if(faulty_nodes == 0){
                logger.warn("This network no longer contains enough nodes to be fault tolerant");
            }

            state.setFaultyNodes(faulty_nodes);
        }
    }

    /**
     * compares two collections of byte arrays, allows for duplicates
     * @param a one collection of byte arrs
     * @param b another collection of byte arrs
     * @return true if they contain the same elements, false otherwise
     */
    private static boolean listOfByteArrsEquals(Collection<byte[]> a, Collection<byte[]> b) {
        Set<ByteString> aSet = new HashSet<>();
        // put all byte strs from one collection in a set
        for (byte[] bytes : a) {
            ByteString bytestr = ByteString.copyFrom(bytes);
            aSet.add(bytestr);
        }
        // remove the found byte strs from the set, if it didnt exist they are not the same
        for (byte[] bytes : b) {
            ByteString bytestr = ByteString.copyFrom(bytes);
            boolean removed = aSet.remove(bytestr);
            if (!removed) {
                return false;
            }
        }
        // if there is elements left in the set, they are not the same
        return aSet.size() <= 0;


    }

    public void tryPreparing(byte[] blockId){
        ConsensusBlock block = msg_log.getBlockWithId(blockId);
        if (block == null) {
            logger.warn("Block did not exist in the message state when trying to prepare it, state: " + state.toString());
            return;
        }

        if(state.getPhase() == State.Phase.PrePreparing
                && msg_log.hashPrePrepare(state.getSeqNum(), state.getView(), blockId)
                && block.getBlockNum() == state.getSeqNum()){
            try {
                state.switchPhase(State.Phase.Preparing, false);

                state.idle_timeout.stop();

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

    public void onPeerConnected(byte[] peerId){
        boolean peerIsMember = state.getMembers().parallelStream().anyMatch(ba -> Arrays.equals(ba, peerId));
        if(!peerIsMember
                || state.getSeqNum() == 1)
            return;

        try {
            broadcastBootstrapCommit(peerId);
        } catch (InternalError | SerializationError e) {
            logger.error(String.format("Failed to broadcast Bootstrap Commit due to error %s", e));
        }
    }

    public void broadcastBootstrapCommit(byte[] peerId) throws InternalError, SerializationError {
        long view = 0;

        if(state.getSeqNum() != 2){
            ConsensusBlock block = msg_log.getBlockWithId(state.getChainHead());
            if(block == null)
                throw new InternalError(String.format("Node does not have chain head {%s} in its log", Arrays.toString(state.getChainHead())));
            else{
                try {
                    view = PbftSeal.parseFrom(block.getPayload())
                            .getInfo().getView();
                } catch (InvalidProtocolBufferException e) {
                    throw new SerializationError(String.format("Error parsing seal from chain head %s", e));
                }
            }
        }

        PbftMessageInfo commit = MessageExtension.newMessageInfo(Commit, view, state.getSeqNum()-1, state.getPeerId());

        service.sendTo(peerId, "Commit", commit.toByteArray());
    }

    public List<PbftSignedVote> signedVotesFromMessages(List<ParsedMessage> msgs){
        return msgs.stream()
                .map(m -> PbftSignedVote.newBuilder()
                        .setHeaderBytes(ByteString.copyFrom(m.getHeaderBytes()))
                        .setHeaderSignature(ByteString.copyFrom(m.getHeaderSignature()))
                        .setMessageBytes(ByteString.copyFrom(m.getMessageBytes()))
                    .build())
                .collect(Collectors.toList());
    }

    protected static class BlockIdView {
        private final byte[] blockId;
        private final long view;

        public BlockIdView(byte[] blockId, long view) {
            this.blockId = blockId;
            this.view = view;
        }

        public byte[] getBlockId() {
            return blockId;
        }

        public long getView() {
            return view;
    }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockIdView that = (BlockIdView) o;
            return view == that.view &&
                    Arrays.equals(blockId, that.blockId);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(view);
            result = 31 * result + Arrays.hashCode(blockId);
            return result;
        }
    }

    public PbftSeal buildSeal() throws InternalError {
        logger.trace(String.format("%s: Building seal for block %d", state, state.getSeqNum() - 1));

        List<ParsedMessage> msgs = msg_log.getMessageOfTypeSeq(Commit, state.getSeqNum() - 1);
        Map<BlockIdView, List<ParsedMessage>> msgsMap = new HashMap<>();
        List<ParsedMessage> messages = null;

        // filter out
        msgs = msgs.stream()
                .filter(msg -> !msg.fromSelf())
                .collect(Collectors.toList());
        // add to map
        for(ParsedMessage msg : msgs){
            BlockIdView key = new BlockIdView(msg.getBlockId().toByteArray(), msg.info().getView());
            msgsMap.computeIfAbsent(key, k -> new ArrayList<>());
            msgsMap.get(key).add(msg);
        }

        BlockIdView key = null;
        for(Map.Entry<BlockIdView, List<ParsedMessage>> entry : msgsMap.entrySet()){
            if(entry.getValue().size() >= 2 * state.getFaultyNodes()) {
                messages = entry.getValue();
                key = entry.getKey();
                break;
            }
        }

        if(messages == null)
            throw new InternalError("Couldn't find 2f commit messages in the message log for building a seal");

        long view = key.getView();
        byte[] block_id = key.getBlockId();

        PbftSeal.Builder sealBuilder = PbftSeal.newBuilder()
                .setInfo(
                        MessageExtension.newMessageInfo(Seal, view, state.getSeqNum()-1, state.getPeerId())
                )
                .setBlockId(ByteString.copyFrom(block_id));

        PbftSeal seal = sealBuilder.addAllCommitVotes(signedVotesFromMessages(messages)).build();

        logger.trace("Seal created: " + logMessage(seal));

        return seal;
    }

    @FunctionalInterface
    public interface InvalidMessageFunction<T, R> {
        R apply(T t) throws InvalidMessage;
    }

    public <R> byte[] verifyVote(PbftSignedVote vote, MessageType expected_type, InvalidMessageFunction<PbftMessage,R> validation_criteria) throws SerializationError, InvalidMessage, SigningError {
        PbftMessage pbft_message;
        ConsensusPeerMessageHeader header;

        try {
            pbft_message = PbftMessage.parseFrom(vote.getMessageBytes());
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationError(String.format("Error parsing PbftMessage from vote, %s", e));
        }

        try {
            header = ConsensusPeerMessageHeader.parseFrom(vote.getHeaderBytes());
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationError(String.format("Error parsing header from vote, %s", e));
        }

        logger.trace(String.format("Verifying vote with PbftMessage: {%s} and header: {%s}", pbft_message, header));

        if(!header.getSignerId().equals(pbft_message.getInfo().getSignerId())) {
            throw new InvalidMessage(String.format("Received a vote where PbftMessage's signer ID {%s} " +
                            "and PeerMessage's signer ID {%s} dont match",
                    pbft_message.getInfo().getSignerId(),
                    header.getSignerId()));
        }

        MessageType msg_type = MessageType.valueOf(pbft_message.getInfo().getMsgType());

        if(msg_type != expected_type)
            throw new InvalidMessage(String.format("Received a {%s} vote, but expected a {%s}", msg_type, expected_type));

        Secp256k1PublicKey key = Secp256k1PublicKey.fromHex(HexBin.encode(header.getSignerId().toByteArray()));

        Context context = CryptoFactory.createContext("secp256k1");

        if(!context.verify(HexBin.encode(vote.getHeaderSignature().toByteArray()),
                vote.getHeaderBytes().toByteArray(),
                key)){
            throw new SigningError(String.format("Vote (%s) failed signature verification", vote));
        }

        try {
            verifySha512(vote.getMessageBytes().toByteArray(), header.getContentSha512().toByteArray());
        }
        catch(SigningError e){
            return null;
        }

        try {
            validation_criteria.apply(pbft_message);
        }
        catch(InvalidMessage e){
            return null;
        }

        return pbft_message.getInfo().getSignerId().toByteArray();
    }

    public void verifyNewView(PbftNewView new_view) throws InvalidMessage {
        if(new_view.getInfo().getView() <= state.getView()){
            throw new InvalidMessage(String.format("Node is on view {%d}, but received NewView message for view {%d}",
                    state.getView(),
                    new_view.getInfo().getView()));
        }

        boolean viewPrimariesEquals = Arrays.equals(
                new_view.getInfo().getSignerId().toByteArray(),
                state.getPrimaryIdAtView(new_view.getInfo().getView()));
        if(!viewPrimariesEquals){
            throw new InvalidMessage(String.format("Received NewView message for view {%d} that is not from the primary for that view",
                    new_view.getInfo().getView()));
        }

        List<byte[]> voter_ids = null;
        for(PbftSignedVote vote : new_view.getViewChangesList()){
            byte[] id;
            try {
                id = verifyVote(vote, ViewChange, msg -> {
                    if (msg.getInfo().getView() != new_view.getInfo().getView()) {
                        throw new InvalidMessage(String.format("ViewChange's view number (%d) doesn't match NewView's view number (%d)",
                                msg.getInfo().getView(),
                                new_view.getInfo().getView()));
                    }
                    return msg;
                });
            } catch (SerializationError | SigningError | InvalidMessage e) {
                voter_ids = null;
                break;
            }

            voter_ids.add(id);
        }

        List<byte[]> peer_ids = state.getMembers()
                .stream()
                .filter(pid -> !Arrays.equals(pid, new_view.getInfo().getSignerId().toByteArray()))
                .collect(Collectors.toList());

        logger.trace(String.format("Comparing voter IDs (%s) with member IDs - primary (%s)", voter_ids, peer_ids));

        if(Collections.indexOfSubList(peer_ids, voter_ids) == -1){
            List<byte[]> newsub = new ArrayList<>(peer_ids); // clone
            throw new InvalidMessage(String.format("NewView contains vote(s) from invalid IDs: %s",
                    newsub.removeAll(voter_ids)
                    ));
        }

        if(voter_ids.size() < 2 * state.getFaultyNodes()){
            throw new InvalidMessage(String.format("NewView needs {%d} votes, but only {%d} found",
                    2 * state.getFaultyNodes(),
                    voter_ids.size()));
        }
    }

    public PbftSeal verifyConsensusSealFromBlock(ConsensusBlock block) throws InvalidMessage, SerializationError, InternalError {

        if(block.getBlockNum() < 2)
            return PbftSeal.newBuilder().build();

        if(block.getPayload().isEmpty())
            throw new InvalidMessage("Block published without a seal");

        PbftSeal seal;
        try {
            seal = PbftSeal.parseFrom(block.getPayload().toByteArray());
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationError("Error parsing seal for verification");
        }

        logger.trace(String.format("Parsed seal: %s", logMessage(seal)));

        if(!seal.getBlockId().equals(block.getPreviousId()))
            throw new InvalidMessage(String.format("Seal's ID (%s) doesn't match block's previous ID (%s)",
                    HexBin.encode(seal.getBlockId().toByteArray()),
                    HexBin.encode(block.getPreviousId().toByteArray())));


        ConsensusBlock proven_block_previous_id = msg_log.getBlockWithId(seal.getBlockId().toByteArray());

        if(proven_block_previous_id == null)
            throw new InternalError(String.format("Got seal for block {%s}, but block was not found in the log",
                    seal.getBlockId()));

        try {
            verifyConsensusSeal(seal, proven_block_previous_id.getBlockId().toByteArray());
        } catch (InvalidMessage invalidMessage) {
            return null;
        }

        return seal;
    }

    public void verifyConsensusSeal(PbftSeal seal, byte[] previousId) throws InvalidMessage {
        List<byte[]> voter_ids = new ArrayList<>();

        for(PbftSignedVote vote : seal.getCommitVotesList()){
            byte[] id;
            try {
                id = verifyVote(vote, Commit, msg -> {
                    if (!msg.getBlockId().equals(seal.getBlockId()))
                        throw new InvalidMessage("Commit vote's block ID (" + msg.getBlockId() + ") doesn't match seal's ID (" + seal.getBlockId() + ")");

                    if (msg.getInfo().getView() != seal.getInfo().getView())
                        throw new InvalidMessage("Commit vote's view (" + msg.getInfo().getView() + ") doesn't match seal's view (" + seal.getInfo().getView() + ")");

                    if (msg.getInfo().getSeqNum() != seal.getInfo().getSeqNum())
                        throw new InvalidMessage("Commit vote's seqnum (" + msg.getInfo().getSeqNum() + ") doesn't match seal's seqnum (" + seal.getInfo().getSeqNum() + ")");

                    return msg;
                });
            } catch (SerializationError | SigningError e) {
                throw new RuntimeException(e);
            } catch (InvalidMessage e) {
                throw e;
            }
            voter_ids.add(id);
        }

        logger.trace("Getting on-chain list of members to verify seal");

        RetryUntilOk retryUntilOk = new RetryUntilOk(state.exponential_retry_base, state.exponential_retry_max);

        // All of the votes in a seal must come from PBFT members, and the primary can't explicitly
        // vote itself, since building a consensus seal is an implicit vote. Check that the votes
        // received are from a subset of "members - seal creator". Use the list of members from the
        // block previous to the one this seal verifies, since that represents the state of the
        // network at the time this block was voted on.
        Map<String, String> settings = retryUntilOk.run(() -> service.getSettings(previousId,
                Arrays.asList("sawtooth.consensus.pbft.members")
        ));

        List<byte[]> members = Config.getMembersFromSettings(settings);


        boolean sealSignerInMembers = members.parallelStream()
                .anyMatch(mem -> Arrays.equals(mem, seal.getInfo().getSignerId().toByteArray()));
        if(!sealSignerInMembers)
            throw new InvalidMessage(String.format("Consensus seal is signed by an unknown peer: %s", seal.getInfo().getSignerId()));

        List<byte[]> peer_ids = members
                .stream()
                .filter(pid -> !Arrays.equals(pid, seal.getInfo().getSignerId().toByteArray()))
                .collect(Collectors.toList());

        logger.trace(String.format("Comparing voter IDs (%s) with on-chain member IDs - primary (%s)", voter_ids, peer_ids));

        // check if voter ids is a subset of the peer ids
        boolean voterIdsAreSubsetOfPeerIds = voter_ids.parallelStream()
                .allMatch(id -> peer_ids.parallelStream().anyMatch(pi -> Arrays.equals(pi, id)));
        if(!voterIdsAreSubsetOfPeerIds){
            List<byte[]> newsub = new ArrayList<>(peer_ids); // clone
            throw new InvalidMessage(String.format("Consensus seal contains vote(s) from invalid ID(s): %s",
                    newsub.removeAll(voter_ids)
            ));
        }

        if(voter_ids.size() < 2 * state.getFaultyNodes())
            throw new InvalidMessage(String.format("Consensus seal needs {%d} votes, but only {%d} found", 2 * state.getFaultyNodes(), voter_ids.size()));
    }

    public void tryPublish() throws ServiceError {
        if(!state.isPrimary() || state.getPhase() != State.Phase.PrePreparing)
            return;

        logger.trace(String.format("%s: Attempting to summarize block", state));

        try {
            service.summarizeBlock();
        } catch (InvalidStateException | BlockNotReadyException | ReceiveErrorException e) {
            logger.trace(String.format("Couldn't summarize, so not finalizing: %s", e));
            return;
        }

        // We don't publish a consensus seal at block 1, since we never receive any
        // votes on the genesis block. Leave payload blank for the first block.
        byte[] data = new byte[]{};

        if(state.getSeqNum() > 1) {
            try {
                data = buildSeal().toByteArray();
            } catch (InternalError internalError) {
                internalError.printStackTrace();
            }
        }

        try {
            byte[] block_id = service.finalizeBlock(data);

            logger.info(String.format("{%s}: Publishing block {%s}", state, HexBin.encode(block_id).substring(0, 6)));
        } catch (InvalidStateException | UnknownBlockException | ReceiveErrorException | BlockNotReadyException e) {
            throw new ServiceError(String.format("Couldn't finalize block: %s", e));
        }
    }

    public boolean checkIdleTimeoutExpired(){
        return state.idle_timeout.checkExpired();
    }

    public void startIdleTimeout(){
        state.idle_timeout.start();
    }

    public boolean checkCommitTimeoutExpired(){
        return state.commit_timeout.checkExpired();
    }

    public void startCommitTimeout(){
        state.commit_timeout.start();
    }

    public boolean checkViewChangeTimeoutExpired(){
        return state.view_change_timeout.checkExpired();
    }

    public void broadcastPBFTMessage(long view, long seq_num, MessageType msg_type, byte[] blockId){
        PbftMessage msg = PbftMessage.newBuilder()
                .setBlockId(ByteString.copyFrom(blockId))
                .setInfo(
                        MessageExtension.newMessageInfo(msg_type, view, seq_num, state.getPeerId())
                )
                .build();

        logger.trace(String.format("%s: : Created PBFT message: %s", state, msg));

        broadcastMessage(new ParsedMessage(msg));
    }

    public void broadcastMessage(ParsedMessage msg) {
        service.broadcast(msg.info().getMsgType(),
                msg.getMessageBytes());

        try {
            onPeerMessage(msg);
        } catch (InvalidMessage invalidMessage) {
            throw new RuntimeException("Engine failed to handle its own peer message");
        }
    }

    public void sendSealResponse(byte[] recipient) throws InternalError {
        PbftSeal seal;
        try {
            seal = buildSeal();
        } catch (InternalError e) {
            throw new InternalError(String.format("Failed to build requested seal due to: %s", e));
        }

        byte[] msg_bytes = seal.toByteArray();

        service.sendTo(
                recipient,
                "Seal",
                msg_bytes
        );
    }

    public void startViewChange(long view){
        if(state.getMode() == State.Mode.ViewChanging
                && view <= state.getMode().getViewChanging()){
            return;
        }

        state.setModeViewChanging(view);

        state.idle_timeout.stop();
        state.commit_timeout.stop();


        state.view_change_timeout.stop();

        broadcastPBFTMessage(
                view,
                state.getSeqNum() - 1,
                ViewChange,
                new byte[]{}
        );
    }

    public State getState(){
        return state;
    }
}

