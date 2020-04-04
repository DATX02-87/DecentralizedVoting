package se.chalmers.datx02.model.state;

import java.util.*;

public class GlobalStateBuilder {
    private Collection<String> admins = new ArrayDeque<>();
    private Map<String, Election> elections = new HashMap<>();

    public GlobalStateBuilder setAdmins(Collection<String> admins) {
        this.admins = admins;
        return this;
    }

    public GlobalStateBuilder setElections(Map<String, Election> elections) {
        this.elections = elections;
        return this;
    }

    public static GlobalStateBuilder fromGlobalState(GlobalState state) {
        GlobalStateBuilder builder = new GlobalStateBuilder();
        builder.setAdmins(new ArrayList<>(state.getAdmins()));
        builder.setElections(new HashMap<>(state.getElections()));
        return builder;
    }

    public GlobalState createGlobalState() {
        return new GlobalState(admins, elections);
    }
}