import React from 'react';
import PropTypes from 'prop-types';
import Card from 'react-bootstrap/Card';

const VotationItem = ({ votation: { name, active, hasVoted } }) => {
  return (
    <div>
      <Card style={{ width: '18rem' }}>
        <Card.Body>
          <Card.Title>{name}</Card.Title>
          <Card.Subtitle className='mb-2 text-muted'>
            Active: {active}
          </Card.Subtitle>
          <Card.Text>Voted: {hasVoted}</Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
};

VotationItem.propTypes = {
  votation: PropTypes.object.isRequired,
};

export default VotationItem;
