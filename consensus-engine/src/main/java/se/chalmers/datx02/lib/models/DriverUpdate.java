package se.chalmers.datx02.lib.models;

import sawtooth.sdk.protobuf.Message;

public class DriverUpdate {
    private final Message.MessageType messageType;
    private final byte[] data;

    public DriverUpdate(Message.MessageType messageType, byte[] data) {
        this.messageType = messageType;
        this.data = data;
    }

    public Message.MessageType getMessageType() {
        return messageType;
    }

    public byte[] getData() {
        return data;
    }
}
