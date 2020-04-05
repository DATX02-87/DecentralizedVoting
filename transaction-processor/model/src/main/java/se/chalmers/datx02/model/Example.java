package se.chalmers.datx02.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import sawtooth.sdk.protobuf.TpProcessRequest;
import se.chalmers.datx02.model.action.AddCandidateAction;
import se.chalmers.datx02.model.action.AddElectionAction;
import se.chalmers.datx02.model.action.CastVoteAction;
import se.chalmers.datx02.model.action.EndElectionAction;
import se.chalmers.datx02.model.exception.InvalidStateException;
import se.chalmers.datx02.model.exception.ReducerException;
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
        t = new Transaction(Action.ADD_CANDIDATE, om.writeValueAsString(new AddCandidateAction("Steffe", "TestVal")), "Melker");
        newState = Reducer.applyTransaction(t, newState);
        t = new Transaction(Action.ADD_CANDIDATE, om.writeValueAsString(new AddCandidateAction("Uffe", "TestVal")), "Anders");
        newState = Reducer.applyTransaction(t, newState);
        t = new Transaction(Action.CAST_VOTE, om.writeValueAsString(new CastVoteAction("TestVal", "Steffe")), "Kula");
        newState = Reducer.applyTransaction(t, newState);
        t = new Transaction(Action.END_ELECTION, om.writeValueAsString(new EndElectionAction("TestVal")), "Anders");
        newState = Reducer.applyTransaction(t, newState);
        System.out.println(newState);
        t = new Transaction(Action.CAST_VOTE, om.writeValueAsString(new CastVoteAction("TestVal", "Uffe")), "Kalle");
        newState = Reducer.applyTransaction(t, newState);
    }

    public void exampleTransactionProcessing(TpProcessRequest processRequest) throws InvalidStateException, ReducerException {
        // for example only
        GlobalState g = null;

        String payload = processRequest.getPayload().toStringUtf8();
        String submitter = processRequest.getHeader().getSignerPublicKey();
        TransactionPayload transactionPayload = DataUtil.StringToTransactionPayload(payload);
        Transaction transaction = new Transaction(transactionPayload, submitter);
        GlobalState newState = Reducer.applyTransaction(transaction, g);
    }
}
