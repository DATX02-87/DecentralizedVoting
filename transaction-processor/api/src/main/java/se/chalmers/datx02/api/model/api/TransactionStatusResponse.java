package se.chalmers.datx02.api.model.api;


public class TransactionStatusResponse {
    private final TransactionStatus status;

    public TransactionStatusResponse(String status) {
        this.status = TransactionStatus.valueOf(status);
    }

    public TransactionStatus getStatus() {
        return status;
    }
    private static enum TransactionStatus {
        COMMITTED, PENDING, INVALID, UNKNOWN
    }
}
