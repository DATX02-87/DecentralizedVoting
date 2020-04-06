import React from 'react';
import PropTypes from 'prop-types';
import ListGroup from 'react-bootstrap/ListGroup';

const Votations = ({ votations }) => {
  return (
    <div>
      <h1>Render all available votations here</h1>
      <ListGroup>
        {votations.map(listItem => (
          <ListGroup.Item>{listItem}</ListGroup.Item>
        ))}
      </ListGroup>
    </div>
  );
};

Votations.PropTypes = {
  votations: PropTypes.array.isRequired
};

export default Votations;
