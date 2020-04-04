package se.chalmers.datx02.model.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCandidateAction {
    private final String candidate;
    private final String electionName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AddCandidateAction(@JsonProperty("candidate") String candidate, @JsonProperty("electionName") String electionName) {
        this.candidate = candidate;
        this.electionName = electionName;
    }

    public String getCandidate() {
        return candidate;
    }

    public String getElectionName() {
        return electionName;
    }
}
