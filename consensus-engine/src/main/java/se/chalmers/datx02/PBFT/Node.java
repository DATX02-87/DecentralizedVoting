package se.chalmers.datx02.PBFT;

import pbft.sdk.protobuf.PbftNewView;
import pbft.sdk.protobuf.PbftSeal;
import pbft.sdk.protobuf.PbftSignedVote;
import sawtooth.sdk.protobuf.Block;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusPeerInfo;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.PBFT.message.MessageExtension;
import se.chalmers.datx02.PBFT.message.MessageType;
import se.chalmers.datx02.PBFT.message.ParsedMessage;
import se.chalmers.datx02.lib.Service;

import java.util.List;
import java.util.function.Function;

public class Node {
    private Service service;
    private MessageLog msg_log;

    public Node(Config config, ConsensusBlock chainHead, List<ConsensusPeerInfo> connected_peers, Service service, State state){

    }

    // TODO: Impl
    // TODO: Throw exceptions

    public void onPeerMessage(Message msg, State state){

    }

    public void handlePrePrepare(Message msg, State state){

    }

    public void handlePrepare(Message msg, State state){

    }

    public void handleCommit(Message msg, State state){

    }

    public void handleViewChange(Message msg, State state){

    }

    public void handleNewView(Message msg, State state){

    }

    public void handleSealRequest(Message msg, State state){

    }

    public void handleSealResponse(Message msg, State state){

    }

    public void onBlockNew(Block block, State state){

    }

    public void onBlockValid(byte[] blockId, State state){

    }

    public void onBlockInvalid(byte[] blockId){

    }

    public void tryHandlingBlock(Block block, State state){

    }

    // Add Seal messagetype to parameters
    public void catchup(boolean catchup_again, State state){

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

    public PbftSeal verifyConsensusSealFromBlock(Block block, State state){
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
