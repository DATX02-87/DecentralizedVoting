package se.chalmers.datx02.model.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class AddElectionAction {
    private final String name;

    private final List<String> admins;

    private final List<String> allowedVoters;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AddElectionAction(@JsonProperty("name") String name, @JsonProperty("admins") List<String> admins, @JsonProperty("allowedVoters") List<String> allowedVoters) {
        this.name = name;
        this.admins = Collections.unmodifiableList(admins);
        this.allowedVoters = Collections.unmodifiableList(allowedVoters);
    }

    public String getName() {
        return name;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public List<String> getAllowedVoters() {
        return allowedVoters;
    }
}
