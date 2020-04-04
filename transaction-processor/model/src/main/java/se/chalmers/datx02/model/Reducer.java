package se.chalmers.datx02.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.chalmers.datx02.model.action.AddCandidateAction;
import se.chalmers.datx02.model.action.AddElectionAction;
import se.chalmers.datx02.model.action.CastVoteAction;
import se.chalmers.datx02.model.action.EndElectionAction;
import se.chalmers.datx02.model.exception.InvalidStateException;
import se.chalmers.datx02.model.state.GlobalState;

public class Reducer {
    private static ObjectMapper mapper = new ObjectMapper();

    public static GlobalState applyTransaction(Transaction transaction, GlobalState currentState) throws InvalidStateException {
        try {
            switch (transaction.getAction()) {
                case ADD_ELECTION:
                    AddElectionAction addElectionAction = mapper.readValue(transaction.getPayload(), AddElectionAction.class);
                    return handleAddElectionAction(addElectionAction, transaction.getSubmitter(), currentState);
                case ADD_CANDIDATE:
                    AddCandidateAction addCandidateAction = mapper.readValue(transaction.getPayload(), AddCandidateAction.class);
                    return handleAddCandidateAction(addCandidateAction, transaction.getSubmitter(), currentState);
                case CAST_VOTE:
                    CastVoteAction castVoteAction = mapper.readValue(transaction.getPayload(), CastVoteAction.class);
                    return handleCastVoteAction(castVoteAction, transaction.getSubmitter(), currentState);
                case END_ELECTION:
                    EndElectionAction endElectionAction = mapper.readValue(transaction.getPayload(), EndElectionAction.class);
                    return handleEndElectionAction(endElectionAction, transaction.getSubmitter(), currentState);
            }
        } catch (JsonMappingException e) {
            // TODO
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO
            e.printStackTrace();
        }
        throw new InvalidStateException();
    }

    private static GlobalState handleEndElectionAction(EndElectionAction action, String submitter, GlobalState currentState) {
        // TODO
        return null;
    }

    private static GlobalState handleCastVoteAction(CastVoteAction action, String submitter, GlobalState currentState) {
        // TODO
        return null;
    }

    private static GlobalState handleAddCandidateAction(AddCandidateAction action, String submitter, GlobalState currentState) {
        // TODO
        return null;
    }

    private static GlobalState handleAddElectionAction(AddElectionAction action, String submitter, GlobalState currentState) {
        // TODO
        return null;
    }
}
