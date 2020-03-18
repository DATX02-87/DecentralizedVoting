package se.chalmers.datx02.lib.models;

import com.google.protobuf.ByteString;
import sawtooth.sdk.protobuf.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsensusFuture {
    private final Message.MessageType requestType;
    private CompletableFuture<ConsensusFutureResponse> future = null;

    public ConsensusFuture(Message.MessageType requestType) {
        future = new CompletableFuture<>();
        this.requestType = requestType;
    }

    public boolean done() {
        return future.isDone();
    }

    public ConsensusFutureResponse result(long timeout) {
        try {
            return future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ConsensusFutureResponse result() {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setResponse(byte[] content, Message.MessageType messageType) {
        this.future.complete(new ConsensusFutureResponse(content, messageType));
    }

    public static class ConsensusFutureResponse {
        private final byte[] content;
        private final Message.MessageType messageType;

        private ConsensusFutureResponse(byte[] content, Message.MessageType messageType) {
            this.content = content;
            this.messageType = messageType;
        }

        public byte[] getContent() {
            return content;
        }

        public Message.MessageType getMessageType() {
            return messageType;
        }
    }
}
