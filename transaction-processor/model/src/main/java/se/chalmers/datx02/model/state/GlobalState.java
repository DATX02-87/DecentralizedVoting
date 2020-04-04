package se.chalmers.datx02.model.state;

import java.io.Serializable;
import java.util.*;

public class GlobalState implements Serializable {
    static final long serialVersionUID = 1L;

    private final Set<String> admins;

    private final Map<String, Election> elections;

    public GlobalState(Collection<String> admins, Map<String, Election> elections) {
        this.admins = Collections.unmodifiableSet(new HashSet<>(admins));
        this.elections = Collections.unmodifiableMap(elections);
    }

    public Set<String> getAdmins() {
        return admins;
    }

    public Map<String, Election> getElections() {
        return elections;
    }

    @Override
    public String toString() {
        return "GlobalState{" +
                "admins=" + admins +
                ", elections=" + elections +
                '}';
    }
}
