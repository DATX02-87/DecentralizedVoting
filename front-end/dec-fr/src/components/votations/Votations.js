import React, { useContext, useEffect, useState } from 'react';
import ListGroup from 'react-bootstrap/ListGroup';
import { KeyContext } from '../context/KeyContext';
import VotationItem from './VotationItem';

import { getEligibleElections } from '../../services/api';

const Votations = () => {
  const [key] = useContext(KeyContext);
  const [votations, setVotations] = useState([]);

  useEffect(() => {
    const getElections = async () => {
      if (key) {
        const data = await getEligibleElections(key);
        console.log(data);
        setVotations(data);
      }
    };
    getElections();
  }, [key]);

  return (
    <div>
      <h1>Render all available votations here</h1>
      <ListGroup>
        {votations.map((votation) => (
          <VotationItem votation={votation} />
        ))}
      </ListGroup>
    </div>
  );
};

export default Votations;
