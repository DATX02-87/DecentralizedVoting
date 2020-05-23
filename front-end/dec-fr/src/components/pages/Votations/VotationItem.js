import React from 'react';
import PropTypes from 'prop-types';
import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';
import { Row, Col } from 'react-bootstrap';

const VotationItem = ({ votation: { name, active, hasVoted, eligible, admin } }) => {
  return (
    <div>
      <Card className='card'>
        <Card.Body>
          <Row>
            <Col>
              <Card.Title>{name}</Card.Title>
              <Card.Text>Active: {active.toString()}</Card.Text>
              {eligible ? <Card.Text>Voted: {hasVoted.toString()}</Card.Text> : null}
              {admin ? <Card.Text>Administrator</Card.Text> : null}
            </Col>
            <Col>
              <Link
                className='btn btn-dark btn-sm my-1'
                to={`/votations/${name}`}
              >
                Go to votation
              </Link>
            </Col>
          </Row>
        </Card.Body>
      </Card>
    </div>
  );
};

VotationItem.propTypes = {
  votation: PropTypes.object.isRequired,
};

export default VotationItem;
