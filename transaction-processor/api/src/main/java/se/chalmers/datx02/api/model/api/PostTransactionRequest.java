package se.chalmers.datx02.api.model.api;

import se.chalmers.datx02.model.TransactionPayload;

public class PostTransactionRequest {
    private String signature;
    private byte[] header;
    private TransactionPayload payload;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public TransactionPayload getPayload() {
        return payload;
    }

    public void setPayload(TransactionPayload payload) {
        this.payload = payload;
    }
}
