package se.chalmers.datx02.api.model.api;

public class PostTransactionResponse {
    private String transactionId;
    private String batchId;

    public PostTransactionResponse(String transactionId, String batchId) {
        this.transactionId = transactionId;
        this.batchId = batchId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
