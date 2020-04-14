import React from 'react';
import PropTypes from 'prop-types';
import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';

const VotationItem = ({ votation: { name, active, hasVoted } }) => {
  return (
    <div>
      <Card style={{ width: '30rem' }}>
        <Card.Body>
          <Card.Title>{name}</Card.Title>
          <Card.Text>Active: {active.toString()}</Card.Text>
          <Card.Text>Voted: {hasVoted.toString()}</Card.Text>
          <Link to={`/votation/${name}`}>Go to Election</Link>
        </Card.Body>
      </Card>
    </div>
  );
};

VotationItem.propTypes = {
  votation: PropTypes.object.isRequired,
};

export default VotationItem;
