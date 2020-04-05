package se.chalmers.datx02.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionPayload {
    private final Action action;
    private final String data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TransactionPayload(@JsonProperty("action") Action action, @JsonProperty("data") String data) {
        this.action = action;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}
