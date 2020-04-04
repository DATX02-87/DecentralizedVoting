package se.chalmers.datx02.model.action;

import java.util.Collections;
import java.util.List;

public class AddElectionAction {
    private final String name;

    private final List<String> admins;

    public AddElectionAction(String name, List<String> admins) {
        this.name = name;
        this.admins = Collections.unmodifiableList(admins);
    }
}
