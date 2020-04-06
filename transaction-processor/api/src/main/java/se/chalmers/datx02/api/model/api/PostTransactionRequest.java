package se.chalmers.datx02.api.model.api;

import se.chalmers.datx02.model.TransactionPayload;

public class PostTransactionRequest {
    private String signature;
    private String header;
    private TransactionPayload payload;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public TransactionPayload getPayload() {
        return payload;
    }

    public void setPayload(TransactionPayload payload) {
        this.payload = payload;
    }
}
