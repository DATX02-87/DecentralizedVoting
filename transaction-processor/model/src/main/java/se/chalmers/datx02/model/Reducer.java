package se.chalmers.datx02.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import se.chalmers.datx02.model.action.AddCandidateAction;
import se.chalmers.datx02.model.action.AddElectionAction;
import se.chalmers.datx02.model.action.CastVoteAction;
import se.chalmers.datx02.model.action.EndElectionAction;
import se.chalmers.datx02.model.exception.InvalidStateException;
import se.chalmers.datx02.model.exception.ReducerException;
import se.chalmers.datx02.model.state.Election;
import se.chalmers.datx02.model.state.ElectionBuilder;
import se.chalmers.datx02.model.state.GlobalState;
import se.chalmers.datx02.model.state.GlobalStateBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Reducer {
    private static ObjectMapper mapper = new ObjectMapper();

    public static GlobalState applyTransaction(Transaction transaction, GlobalState currentState) throws InvalidStateException, ReducerException {
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
            throw new InvalidStateException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        throw new InvalidStateException();
    }

    private static GlobalState handleEndElectionAction(EndElectionAction action, String submitter, GlobalState currentState) throws ReducerException {
        if (StringUtils.isEmpty(action.getElectionName())) {
            throw new ReducerException("Election name supplied should not be empty");
        }
        String electionName = action.getElectionName();

        Election election = currentState.getElections().get(electionName);
        if (election == null) {
            throw new ReducerException("Election with name does not exist");
        }

        // only checks for allowance of election admins, not for master admins
        if (!election.getAdmins().contains(submitter)) {
            throw new ReducerException("User is not authorised to end election");
        }

        if (!election.isActive()) {
            throw new ReducerException("Election is not active");
        }

        Election replacedElection = ElectionBuilder.fromElection(election)
                .setActive(false)
                .createElection();
        HashMap<String, Election> elections = new HashMap<>(currentState.getElections());
        elections.put(electionName, replacedElection);
        return GlobalStateBuilder.fromGlobalState(currentState)
                .setElections(elections)
                .createGlobalState();
    }

    private static GlobalState handleCastVoteAction(CastVoteAction action, String submitter, GlobalState currentState) {
        // TODO
        return null;
    }

    private static GlobalState handleAddCandidateAction(AddCandidateAction action, String submitter, GlobalState currentState) {
        // TODO
        return null;
    }

    private static GlobalState handleAddElectionAction(AddElectionAction action, String submitter, GlobalState currentState) throws ReducerException {
        if (!currentState.getAdmins().contains(submitter)) {
            throw new ReducerException("User is not authorized to add election");
        }
        if (StringUtils.isEmpty(action.getName())) {
            throw new ReducerException("Empty election name is not allowed");
        }
        List<String> admins = action.getAdmins();
        if (admins.isEmpty()) {
            throw new ReducerException("Empty admin list not allowed for new elections");
        }
        List<String> allowedVoters = action.getAllowedVoters();
        if (allowedVoters.isEmpty()) {
            throw new ReducerException("Empty allowed voters is not allowed for new elections");
        }
        Election newElection = new ElectionBuilder()
                .setActive(true)
                .setAdmins(admins)
                .setCandidates(Collections.emptyMap())
                .setVoters(allowedVoters.stream().collect(Collectors.toMap(d -> d, d -> false)))
                .createElection();

        HashMap<String, Election> elections = new HashMap<>(currentState.getElections());
        if (elections.containsKey(action.getName())) {
            throw new ReducerException("An election with the name already exists");
        }
        elections.put(action.getName(), newElection);
        return GlobalStateBuilder.fromGlobalState(currentState)
                .setElections(elections)
                .createGlobalState();
    }
}
