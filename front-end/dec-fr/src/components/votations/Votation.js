import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Row, ButtonGroup, Spinner } from 'react-bootstrap';

import { castVote } from '../../services/api';
import VoteContext from '../context/vote/voteContext';
import KeyContext from '../context/key/keyContext';

const Votation = ({ match }) => {
  const voteContext = useContext(VoteContext);
  const { loading, getElection } = voteContext;

  const keyContext = useContext(KeyContext);
  const { key } = keyContext;
  const [vote, setVote] = useState({});
  const votation = getElection(match.params.name, key);

  const { name, active, hasVoted, candidates } = votation;

  const onChange = (e) => setVote(e.target.value);

  const onSubmit = (e) => {
    e.preventDefault();
    if (vote) {
      castVote(key, votation.name, vote);
      console.log(key + ' has voted on ' + vote + ' in ' + votation.name);
    }

    castVote(key, votation.name);
  };
  console.log(voteContext);
  if (loading || votation === {}) return <Spinner />;
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
