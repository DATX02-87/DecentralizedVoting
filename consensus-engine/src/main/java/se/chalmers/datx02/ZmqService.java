package se.chalmers.datx02;

import com.google.protobuf.ByteString;
import com.google.protobuf.Parser;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.lib.Service;
import zmq.socket.Stream;

import java.util.List;
import java.util.Map;


public class ZmqService implements Service {
    private Stream stream;
    private Double timeout;

    public ZmqService(Stream stream, Double timeout) {
        this.stream = stream;
        this.timeout = timeout;
    }

    private <T> T send(byte[] request, Message.MessageType messageType, Parser<T> responseParser) {
        try {
            byte[] response = new byte[]{}; // = something
            return responseParser.parseFrom(response);
        } catch (Exception e) {
            // TODO
            return null;
        }
    }


    public void sendTo(byte[] receiverId, Message.MessageType messageType, byte[] payload) {
//        ByteString messageContent = ByteString.copyFrom(payload);
//        byte[] request = ConsensusSendToRequest.newBuilder()
//                .setContent(ConsensusPeerMessage.newBuilder()
//                        .setContent(messageContent)
//                        .setMessageType(messageType.name())
//                        .build())
//                .build().toByteArray();
//
//        send(request, Message.MessageType.CONSENSUS_SEND_TO_REQUEST, ConsensusSendToResponse.parser());
    }

    public void broadcast(String messageType, byte[] payload) {

    }

    public void initializeBlock(byte[] previousId) {

    }

    public byte[] summarizeBlock() {
        return new byte[0];
    }

    public byte[] finalizeBlock(byte[] data) {
        return new byte[0];
    }

    public void cancelBlock() {

    }

    public void checkBlocks(List<Byte[]> priority) {

    }

    public void commitBlock(byte[] blockId) {

    }

    public void ignoreBlock(byte[] blockId) {

    }

    public void failBlock(byte[] blockId) {

    }

    @Override
    public Map<Byte[], ConsensusBlock> getBlocks(List<Byte[]> blockIds) {
        return null;
    }

    @Override
    public ConsensusBlock getChainHead() {
        return null;
    }

    public Map<String, String> getSettings(byte[] blockId, List<String> settings) {
        return null;
    }

    public Map<String, Byte[]> getState(byte[] blockId, List<String> addresses) {
        return null;
    }
}
