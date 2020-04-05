package se.chalmers.datx02.model.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Election {
    static final long serialVersionUID = 1L;

    private final List<String> admins;

    private final Map<String, Long> candidates;

    private final Map<String, Boolean> voters;

    private final boolean active;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Election(@JsonProperty("admins") List<String> admins, @JsonProperty("candidates") Map<String, Long> candidates, @JsonProperty("voters") Map<String, Boolean> voters, @JsonProperty("active") boolean active) {
        this.admins = Collections.unmodifiableList(admins);
        this.candidates = Collections.unmodifiableMap(candidates);
        this.voters = Collections.unmodifiableMap(voters);
        this.active = active;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public Map<String, Long> getCandidates() {
        return candidates;
    }

    public Map<String, Boolean> getVoters() {
        return voters;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Election{" +
                "admins=" + admins +
                ", candidates=" + candidates +
                ", voters=" + voters +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Election election = (Election) o;
        return active == election.active &&
                admins.equals(election.admins) &&
                candidates.equals(election.candidates) &&
                voters.equals(election.voters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admins, candidates, voters, active);
    }
}
