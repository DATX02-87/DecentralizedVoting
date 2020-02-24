package se.chalmers.datx02.lib;

import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import java.util.concurrent.Future;

public interface Communicator extends AutoCloseable {
    String getUrl();

    byte[] getZmqId();

    ConsensusFuture send(byte[] content, Message.MessageType messageType);

    void sendBack(byte[] content, String correlationId, Message.MessageType messageType);

    Future<Message> receive();

    void waitForReady();

    boolean isReady();


}
