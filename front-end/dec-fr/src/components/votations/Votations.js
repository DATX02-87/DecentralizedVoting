import React, { useContext, useState, useEffect } from 'react';
import ListGroup from 'react-bootstrap/ListGroup';

import VotationItem from './VotationItem';

import { Spinner } from 'react-bootstrap';
import KeyContext from '../context/key/keyContext';
import { getEligibleElections } from '../../services/api';

const Votations = () => {
  const [ votations, setVotations ] = useState([]);
  const [ loading, setLoading ] = useState(false);
  const { key } = useContext(KeyContext);

  useEffect(() => {
    setLoading(true);
    getEligibleElections(key).then(votations => {
      setVotations(votations);
      setLoading(false);
    });
  }, [ key ]);

  if (loading) {
    return <Spinner />;
  } else {
    return (
      <div>
        <ListGroup>
          {votations.map((votation) => (
            <VotationItem key={votation.name} votation={votation} />
          ))}
        </ListGroup>
      </div>
    );
  }
};

export default Votations;
