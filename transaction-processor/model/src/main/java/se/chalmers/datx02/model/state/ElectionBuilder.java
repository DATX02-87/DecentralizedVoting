package se.chalmers.datx02.model.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectionBuilder {
    private List<String> admins;
    private Map<String, Long> candidates;
    private Map<String, Boolean> voters;
    private boolean active;

    public ElectionBuilder setAdmins(List<String> admins) {
        this.admins = admins;
        return this;
    }

    public ElectionBuilder setCandidates(Map<String, Long> candidates) {
        this.candidates = candidates;
        return this;
    }

    public ElectionBuilder setVoters(Map<String, Boolean> voters) {
        this.voters = voters;
        return this;
    }

    public ElectionBuilder setActive(boolean active) {
        this.active = active;
        return this;
    }

    public static ElectionBuilder fromElection(Election election) {
        return new ElectionBuilder()
                .setActive(election.isActive())
                .setAdmins(new ArrayList<>(election.getAdmins()))
                .setVoters(new HashMap<>(election.getVoters()))
                .setCandidates(new HashMap<>(election.getCandidates()));
    }

    public Election createElection() {
        return new Election(admins, candidates, voters, active);
    }
}