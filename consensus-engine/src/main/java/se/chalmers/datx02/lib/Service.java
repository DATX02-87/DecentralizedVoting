package se.chalmers.datx02.lib;


import com.google.protobuf.ByteString;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.exceptions.*;

import java.util.List;
import java.util.Map;

public interface Service {
    void sendTo(byte[] receiverId, Message.MessageType messageType, byte[] payload);

    void broadcast(String messageType, byte[] payload);

    void initializeBlock(byte[] previousId) throws InvalidStateException, UnknownBlockException, ReceiveErrorException;

    byte[] summarizeBlock() throws InvalidStateException, BlockNotReadyException, ReceiveErrorException;

    byte[] finalizeBlock(byte[] data) throws InvalidStateException, UnknownBlockException, ReceiveErrorException;

    void cancelBlock() throws InvalidStateException, ReceiveErrorException;

    void checkBlocks(List<byte[]> priority) throws UnknownBlockException, ReceiveErrorException;

    void commitBlock(byte[] blockId) throws UnknownBlockException, ReceiveErrorException;

    void ignoreBlock(byte[] blockId) throws UnknownBlockException, ReceiveErrorException;

    void failBlock(byte[] blockId) throws UnknownBlockException, ReceiveErrorException;

    Map<ByteString, ConsensusBlock> getBlocks(List<byte[]> blockIds) throws UnknownBlockException, ReceiveErrorException;

    ConsensusBlock getChainHead() throws NoChainHeadException, ReceiveErrorException;

    Map<String, String> getSettings(byte[] blockId, List<String> settings) throws UnknownBlockException, ReceiveErrorException;

    Map<String, byte[]> getState(byte[] blockId, List<String> addresses) throws UnknownBlockException, ReceiveErrorException;

}
