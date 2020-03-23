package se.chalmers.datx02.PBFT;

import sawtooth.sdk.protobuf.Block;
import sawtooth.sdk.protobuf.ConsensusPeerInfo;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.PBFT.message.PBFTMessage;
import se.chalmers.datx02.lib.Service;

import java.util.List;

public class Node {
    private Service service;
    private MessageLog msg_log;

    public Node(Config config, Block chainHead, List<ConsensusPeerInfo> connected_peers, Service service, State state){

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

    // TODO: Change to new Messagetypes
    public List<Message> signedVotesFromMessages(List<Message> msgs){
        return null;
    }

    // Todo: Change object to seal messagetype
    public Object buildSeal(State state){
        return null;
    }

    // Todo: change object to vote messagetype
    public byte[] verifyVote(Object vote){
        return null;
    }

    // Todo: change object to view messagetype
    public void verifyNewView(Object view, State state){

    }

    // Todo: change object to seal messagetype
    public Object verifyConsensusSealFromBlock(Block block, State state){
        return null;
    }

    // Todo: change object to seal messagetype
    public void verifyConsensusSeal(Object seal, byte[] blockId, State state){

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

    // TODO: Change to correct MessageType
    public void broadcastPBFTMessage(int view, int seq_num, PBFTMessage msg_type, byte[] blockId, State state){

    }

    // TODO: Chnage to correct message
    public void broadcastMessage(Message msg, State state){

    }

    // TODO: Chnage to correct message
    public void sendSealResponse(byte[] recipient, State state){

    }

    public void sendSealResponse(int view, State state){

    }
}
