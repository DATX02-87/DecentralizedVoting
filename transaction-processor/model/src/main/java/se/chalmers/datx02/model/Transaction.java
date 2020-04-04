package se.chalmers.datx02.model;

public class Transaction {
    private final Action action;
    private final String payload;
    private final String submitter;

    public Transaction(Action action, String payload, String submitter) {
        this.action = action;
        this.payload = payload;
        this.submitter = submitter;
    }

    public String getSubmitter() {
        return submitter;
    }

    public Action getAction() {
        return action;
    }

    public String getPayload() {
        return payload;
    }
}
