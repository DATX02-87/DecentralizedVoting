package se.chalmers.datx02.api.model.api;

import se.chalmers.datx02.model.TransactionPayload;

public class TransactionDataToSignRequest {
    private TransactionPayload transactionPayload;
    private String publicKey;

    public TransactionPayload getTransactionPayload() {
        return transactionPayload;
    }

    public void setTransactionPayload(TransactionPayload transactionPayload) {
        this.transactionPayload = transactionPayload;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
