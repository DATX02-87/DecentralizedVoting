package se.chalmers.datx02.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.chalmers.datx02.model.action.AddElectionAction;
import se.chalmers.datx02.model.state.GlobalState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Example {
    public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();

        GlobalState g = new GlobalState(Collections.singletonList("Melker"), new HashMap<>());
        AddElectionAction action = new AddElectionAction("TestVal", Collections.singletonList("Melker"));

        Transaction t = new Transaction(Action.ADD_ELECTION, om.writeValueAsString(action), "Melker");
        GlobalState newState = Reducer.applyTransaction(t, g);
        System.out.println(newState);
    }
}
