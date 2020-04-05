package se.chalmers.datx02.model;

public class Transaction {
    private final TransactionPayload payload;
    private final String submitter;

    public Transaction(Action action, String data, String submitter) {
        this.payload = new TransactionPayload(action, data);
        this.submitter = submitter;
    }

    public Transaction(TransactionPayload payload, String submitter) {
        this.payload = payload;
        this.submitter = submitter;
    }

    public String getSubmitter() {
        return submitter;
    }

    public Action getAction() {
        return payload.getAction();
    }

    public String getPayload() {
        return payload.getData();
    }
}
