package se.chalmers.datx02.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.chalmers.datx02.model.action.AddElectionAction;
import se.chalmers.datx02.model.action.EndElectionAction;
import se.chalmers.datx02.model.state.GlobalState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Example {
    public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();

        GlobalState g = new GlobalState(Collections.singletonList("Melker"), new HashMap<>());
        List<String> allowedVoters = Arrays.asList("Melker", "Kalle", "Kula");
        AddElectionAction action = new AddElectionAction("TestVal", Arrays.asList("Melker", "Anders"), allowedVoters);

        Transaction t = new Transaction(Action.ADD_ELECTION, om.writeValueAsString(action), "Melker");
        GlobalState newState = Reducer.applyTransaction(t, g);
        System.out.println(newState);
        t = new Transaction(Action.END_ELECTION, om.writeValueAsString(new EndElectionAction("TestVal")), "Anders");
        GlobalState newState2 = Reducer.applyTransaction(t, newState);
        System.out.println(newState2);
    }
}
