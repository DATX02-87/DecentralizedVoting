import React from 'react';
import PropTypes from 'prop-types';

const VotationItem = ({ votation: { title, date, description } }) => {
  return (
    <div>
      <Card style={{ width: '18rem' }}>
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Subtitle className='mb-2 text-muted'>{date}</Card.Subtitle>
          <Card.Text>{description}</Card.Text>
          <Card.Link href='#'>Card Link</Card.Link>
          <Card.Link href='#'>Another Link</Card.Link>
        </Card.Body>
      </Card>
    </div>
  );
};

VotationItem.propTypes = {
  votation: PropTypes.object.isRequired
};

export default VotationItem;
