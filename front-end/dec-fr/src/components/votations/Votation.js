import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import { getElection } from '../../services/api';
import { KeyContext } from '../context/KeyContext';

const Votation = () => {
  const keyContext = useContext(KeyContext);
  const { key } = keyContext;
  const { name } = useParams();
  console.log(name);

  const [votation, setVotation] = useState();

  useEffect(() => {
    const getCurrentElection = async () => {
      const data = await getElection(name, key);
      setVotation(data);
    };
    getCurrentElection();
  }, [name, key]);

  return votation ? (
    <Card>
      <Card.Title>Election name: {name}</Card.Title>
      <Card.Body>
        <Card.Text>Active: {votation.active.toString()}</Card.Text>
        <Card.Text>Voted: {votation.hasVoted.toString()}</Card.Text>
      </Card.Body>
    </Card>
  ) : null;
};

export default Votation;
