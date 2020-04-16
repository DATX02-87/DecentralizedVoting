import React, { useContext } from 'react';
import ListGroup from 'react-bootstrap/ListGroup';

import VotationItem from './VotationItem';

import { Spinner } from 'react-bootstrap';
import VoteContext from '../context/vote/voteContext';

const Votations = () => {
  const voteContext = useContext(VoteContext);
  const { loading, votations } = voteContext;

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
