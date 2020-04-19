import React, { useContext, useState, useEffect, useCallback } from 'react';
import { Spinner } from 'react-bootstrap';

import { castVote, getElection } from 'services/api';
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
  if (!votation) {
    return <Spinner />;
  }
  
  return (
    <VotationDisplay {...votation} castingVote={castingVote} onVote={onVote}/>
  );
};

export default Votation;
