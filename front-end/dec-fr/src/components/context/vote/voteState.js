import React, { useReducer } from 'react';
import { getEligibleElections, castVote } from '../../../services/api';
import VoteContext from './voteContext';
import VoteReducer from './voteReducer';
import {
  GET_ELIGIBLE_ELECTIONS_FROM_API,
  SET_LOADING,
  CAST_VOTE_ON_API,
} from '../types';

const VoteState = (props) => {
  const initialState = {
    votations: [],
    votation: {},
    vote: {},
    loading: true,
  };

  const [state, dispatch] = useReducer(VoteReducer, initialState);

  const getEligibleElectionsFromApi = async (key) => {
    setLoading();

    const res = await getEligibleElections(key);

    dispatch({
      type: GET_ELIGIBLE_ELECTIONS_FROM_API,
      payload: res,
    });
  };

  const getElection = (electionName, key) => {
    return state.votations.filter(
      (votation) => votation.name === electionName
    )[0];
  };

  const castVoteOnApi = async (key, electionName, candidate) => {
    setLoading();

    const res = await castVote(key, electionName, candidate);

    dispatch({
      type: CAST_VOTE_ON_API,
      payload: res,
    });
  };

  const setLoading = () => {
    dispatch({ type: SET_LOADING });
  };

  return (
    <VoteContext.Provider
      value={{
        votations: state.votations,
        votation: state.votation,
        vote: state.vote,
        loading: state.loading,
        getEligibleElectionsFromApi,
        castVoteOnApi,
        getElection,
      }}
    >
      {' '}
      {props.children}
      {''}
    </VoteContext.Provider>
  );
};

export default VoteState;
