package se.chalmers.datx02.model.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.*;

public class GlobalState implements Serializable {
    static final long serialVersionUID = 1L;

    private final Set<String> admins;

    private final Map<String, Election> elections;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GlobalState(@JsonProperty("admins") Collection<String> admins, @JsonProperty("elections") Map<String, Election> elections) {
        this.admins = Collections.unmodifiableSet(new HashSet<>(admins));
        this.elections = Collections.unmodifiableMap(elections);
    }

    public GlobalState() {
        this(Collections.emptyList(), Collections.emptyMap());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalState that = (GlobalState) o;
        return admins.equals(that.admins) &&
                elections.equals(that.elections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admins, elections);
    }
}
