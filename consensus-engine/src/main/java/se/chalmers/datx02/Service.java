package se.chalmers.datx02;


import sawtooth.sdk.protobuf.ConsensusBlock;

import java.util.List;
import java.util.Map;

public interface Service {
    void sendTo(byte[] receiverId, String messageType, byte[] payload);

    void broadcast(String messageType, byte[] payload);

    void initializeBlock(byte[] previousId);

    byte[] summarizeBlock();

    byte[] finalizeBlock(byte[] data);

    void cancelBlock();

    void checkBlocks(List<Byte[]> priority);

    void commitBlock(byte[] blockId);

    void ignoreBlock(byte[] blockId);

    void failBlock(byte[] blockId);

    Map<Byte[], ConsensusBlock> getBlocks( List<Byte[]> blockIds);

    ConsensusBlock getChainHead();

    Map<String, String> getSettings(byte[] blockId, List<String> settings);

    Map<String, Byte[]> getState(byte[] blockId, List<String> addresses);

}
