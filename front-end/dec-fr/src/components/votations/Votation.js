import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Row, ButtonGroup, Spinner } from 'react-bootstrap';

import { castVote, getElection } from '../../services/api';
import KeyContext from '../context/key/keyContext';

const Votation = ({ match }) => {
  const { key } = useContext(KeyContext);

  const [selectedCandidate, setSelectedCandidate] = useState(undefined);
  const onChange = (e) => setSelectedCandidate(e.target.value);
  const onSubmit = (e) => {
    e.preventDefault();
    if (!selectedCandidate) {
      throw new Error('A candidate should be selected')
    }
    // TODO 
    castVote(key, votation.name, selectedCandidate);
    return true;
  };

  const [loading, setLoading] = useState(true);
  const [votation, setVotation] = useState(undefined);
  const votationName = match.params.name;
  useEffect(() => {
    setLoading(true);
    getElection(votationName, key).then(votation => {
      setVotation(votation);
      setLoading(false);
    });
  }, [votationName, key]);

  if (loading) {
    return <Spinner />;
  }
  if (!votation) {
    console.error('An error has occured, component is not loading and there is no votation received');
  }
  const { name, active, hasVoted, candidates } = votation;
  return (
    <Card as='div' className='card grid-2'>
      <Row>
        <Col as='div' className='all-center'>
          <Card.Title>Election name: {name}</Card.Title>
          <Card.Body>
            <Card.Text>Active: {active.toString()}</Card.Text>
            <Card.Text>Voted: {hasVoted.toString()}</Card.Text>
          </Card.Body>
        </Col>
        <Col as='div' className='all-center'>
          <Form onSubmit={onSubmit}>
            <Row>
              <Col>
                <fieldset>
                  <Form.Group controlId='voteForm'>
                    <Form.Label as='h5'>Candidates</Form.Label>
                    {candidates.map((candidate) => (
                      <div key={candidate} className='mb-3'>
                        <Form.Check
                          type='radio'
                          name='voteForm'
                          id={candidate}
                          label={candidate}
                          value={candidate}
                          onChange={onChange}
                        />
                      </div>
                    ))}
                  </Form.Group>
                </fieldset>
              </Col>
              <Col>
                <ButtonGroup>
                  <Button size='sm' type='submit'>
                    Submit
                  </Button>
                  <Link to='/' className='btn btn-light'>
                    Back
                  </Link>
                </ButtonGroup>
              </Col>
            </Row>
          </Form>
        </Col>
      </Row>
    </Card>
  );
};

export default Votation;
