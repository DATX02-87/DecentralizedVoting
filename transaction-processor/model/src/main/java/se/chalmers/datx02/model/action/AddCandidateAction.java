package se.chalmers.datx02.model.action;

public class AddCandidateAction {
    private final String candidate;
    private final String electionName;

    public AddCandidateAction(String candidate, String electionName) {
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
