package se.chalmers.datx02.model.state;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Election {
    static final long serialVersionUID = 1L;

    private final List<String> admins;

    private final Map<String, Long> candidates;

    private final Map<String, Boolean> voters;

    private final String name;

    private final boolean active;

    public Election(List<String> admins, Map<String, Long> candidates, Map<String, Boolean> voters, String name, boolean active) {
        this.admins = Collections.unmodifiableList(admins);
        this.candidates = Collections.unmodifiableMap(candidates);
        this.voters = Collections.unmodifiableMap(voters);
        this.name = name;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Election{" +
                "admins=" + admins +
                ", candidates=" + candidates +
                ", voters=" + voters +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
