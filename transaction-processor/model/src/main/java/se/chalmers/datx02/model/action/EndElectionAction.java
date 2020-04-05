package se.chalmers.datx02.model.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EndElectionAction {
    private final String electionName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EndElectionAction(@JsonProperty("electionName") String electionName) {
        this.electionName = electionName;
    }

    public String getElectionName() {
        return electionName;
    }
}
