package se.chalmers.datx02.lib;


import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.exceptions.InvalidState;
import se.chalmers.datx02.lib.exceptions.ReceiveError;
import se.chalmers.datx02.lib.exceptions.UnknownBlock;

import java.util.List;
import java.util.Map;

public interface Service {
    void sendTo(byte[] receiverId, Message.MessageType messageType, byte[] payload);

    void broadcast(String messageType, byte[] payload);

    void initializeBlock(byte[] previousId) throws InvalidState, UnknownBlock, ReceiveError;

    byte[] summarizeBlock();

    byte[] finalizeBlock(byte[] data) throws InvalidState, UnknownBlock, ReceiveError;

    void cancelBlock() throws InvalidState, ReceiveError;

    void checkBlocks(List<byte[]> priority) throws UnknownBlock, ReceiveError;

    void commitBlock(byte[] blockId) throws UnknownBlock, ReceiveError;

    void ignoreBlock(byte[] blockId) throws UnknownBlock, ReceiveError;

    void failBlock(byte[] blockId) throws UnknownBlock, ReceiveError;

    Map<byte[], ConsensusBlock> getBlocks( List<byte[]> blockIds) throws UnknownBlock, ReceiveError;

    ConsensusBlock getChainHead();

    Map<String, String> getSettings(byte[] blockId, List<String> settings) throws UnknownBlock, ReceiveError;

    Map<String, byte[]> getState(byte[] blockId, List<String> addresses) throws UnknownBlock, ReceiveError;

}
