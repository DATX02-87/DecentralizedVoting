package se.chalmers.datx02.model.action;

public class CastVoteAction {
    private final String electionName;
    private final String candidate;

    public CastVoteAction(String electionName, String candidate) {
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
