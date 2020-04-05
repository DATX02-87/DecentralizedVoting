package se.chalmers.datx02.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty
    public Action getAction() {
        return payload.getAction();
    }

    @JsonIgnore
    public String getTransactionData() {
        return payload.getData();
    }

    public TransactionPayload getPayload() {
        return payload;
    }
}
