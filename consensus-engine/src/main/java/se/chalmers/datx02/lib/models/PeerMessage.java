package se.chalmers.datx02.lib.models;

import sawtooth.sdk.protobuf.ConsensusPeerMessageHeader;


public class PeerMessage {
    private final ConsensusPeerMessageHeader header;
    private final byte[] headerBytes;
    private final byte[] headeerSignature;
    private final byte[] content;
    private final byte[] senderId;

    public PeerMessage(ConsensusPeerMessageHeader header, byte[] headerBytes, byte[] headeerSignature, byte[] content, byte[] senderId) {
        this.header = header;
        this.headerBytes = headerBytes;
        this.headeerSignature = headeerSignature;
        this.content = content;
        this.senderId = senderId;
    }

    public byte[] getSenderId() {
        return senderId;
    }

    public ConsensusPeerMessageHeader getHeader() {
        return header;
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }

    public byte[] getHeadeerSignature() {
        return headeerSignature;
    }

    public byte[] getContent() {
        return content;
    }
}
