package se.chalmers.datx02;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

public interface Service {
    void sendTo(@NotNull byte[] receiverId, @NotNull String messageType, @NotNull byte[] payload);

    void broadcast(@NotNull String messageType, @NotNull byte[] payload);

    void initializeBlock(@Nullable byte[] previousId);

    byte[] summarizeBlock();

    byte[] finalizeBlock(@NotNull byte[] data);

    void cancelBlock();

    void checkBlocks(@NotNull List<Byte[]> priority);

    void commitBlock(@NotNull byte[] blockId);

    void ignoreBlock(@NotNull byte[] blockId);

    void failBlock(@NotNull byte[] blockId);

    Map<Byte[], Consensus.ConsensusBlock> getBlocks(@NotNull  List<Byte[]> blockIds);

    Consensus.ConsensusBlock getChainHead();

    Map<String, String> getSettings(byte[] blockId, List<String> settings);

    Map<String, Byte[]> getState(byte[] blockId, List<String> addresses);

}
