package se.chalmers.datx02.model.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InitAction {
    private final List<String> masterAdmins;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public InitAction(@JsonProperty("masterAdmins") List<String> masterAdmins) {
        this.masterAdmins = masterAdmins;
    }

    public List<String> getMasterAdmins() {
        return masterAdmins;
    }
}
