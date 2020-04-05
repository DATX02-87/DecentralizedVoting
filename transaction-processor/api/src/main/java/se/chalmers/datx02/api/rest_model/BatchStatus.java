package se.chalmers.datx02.api.rest_model;

import com.google.api.client.util.Key;

import java.util.List;

public class BatchStatus {
    @Key
    private String id;
    @Key
    private String status;
    @Key(value = "invalid_transactions")
    private List<InvalidTransaction> invalidTransactions;

    public BatchStatus() {
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
