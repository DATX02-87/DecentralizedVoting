package se.chalmers.datx02.model.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CastVoteAction {
    private final String electionName;
    private final String candidate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CastVoteAction(@JsonProperty("electionName") String electionName,@JsonProperty("candidate") String candidate) {
        this.electionName = electionName;
        this.candidate = candidate;
    }

    public String getElectionName() {
        return electionName;
    }

    public String getCandidate() {
        return candidate;
    }
}
