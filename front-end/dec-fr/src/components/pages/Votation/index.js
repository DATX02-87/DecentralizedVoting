import React, { useContext, useState, useEffect, useCallback } from 'react';
import { Spinner } from 'react-bootstrap';

import { castVote, getElection, endElection } from 'services/api';
import KeyContext from 'components/context/key/keyContext';
import VotationDisplay from './VotationDisplay';

const Votation = ({ match }) => {
  const { key } = useContext(KeyContext);

  const [votation, setVotation] = useState(undefined);
  const votationName = match.params.name;
  const getElectionData = useCallback(async () => {
    const votation = await getElection(votationName, key);
    setVotation(votation);
  }, [votationName, key]);

  useEffect(() => {
    getElectionData();
  }, [votationName, key, getElectionData]);

  const [castingVote, setCastingVote] = useState(false);
  const onVote = candidate => {
    setCastingVote(true);
    castVote(key, votation.name, candidate)
      .then(() => getElectionData())
      .then(() => setCastingVote(false));
  }
  const [endingVotation, setEndingVotation] = useState(false)
  const onEndVotation = () => {
    setEndingVotation(true);
    endElection(key, votation.name)
      .then(() => getElectionData())
      .then(() => setEndingVotation(false));
  }
  if (!votation) {
    return <Spinner />;
  }
  
  return (
    <VotationDisplay {...votation} castingVote={castingVote} onVote={onVote} endingVotation={endingVotation} onEndVotation={onEndVotation}/>
  );
};

export default Votation;
