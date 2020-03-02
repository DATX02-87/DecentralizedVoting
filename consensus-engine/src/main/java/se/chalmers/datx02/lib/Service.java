package se.chalmers.datx02.lib;


import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.Message;

import java.util.List;
import java.util.Map;

public interface Service {
    void sendTo(byte[] receiverId, Message.MessageType messageType, byte[] payload);

    void broadcast(String messageType, byte[] payload);

    void initializeBlock(byte[] previousId);

    byte[] summarizeBlock();

    byte[] finalizeBlock(byte[] data);

    void cancelBlock();

    void checkBlocks(List<Byte[]> priority);

    void commitBlock(byte[] blockId);

    void ignoreBlock(byte[] blockId);

    void failBlock(byte[] blockId);

    Map<byte[], ConsensusBlock> getBlocks( List<byte[]> blockIds);

    ConsensusBlock getChainHead();

    Map<String, String> getSettings(byte[] blockId, List<String> settings);

    Map<String, byte[]> getState(byte[] blockId, List<String> addresses);

}
