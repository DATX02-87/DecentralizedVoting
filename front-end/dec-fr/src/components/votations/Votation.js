import React, { useContext, useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import { getElection, castVote } from '../../services/api';
import { KeyContext } from '../context/KeyContext';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Row, ButtonGroup } from 'react-bootstrap';

const Votation = () => {
  const keyContext = useContext(KeyContext);
  const { key } = keyContext;
  const { name } = useParams();

  const [votation, setVotation] = useState();
  const [vote, setVote] = useState();

  useEffect(() => {
    const getCurrentElection = async () => {
      const data = await getElection(name, key);
      setVotation(data);
    };
    getCurrentElection();
  }, [name, key]);

  const onChange = (e) => setVote(e.target.value);

  const onSubmit = (e) => {
    e.preventDefault();
    if (vote) {
      castVote(key, votation.name, vote);
      console.log(key + ' has voted on ' + vote + ' in ' + votation.name);
    }

    castVote(key, votation.name);
  };

  return votation ? (
    <Card as='div' className='card grid-2'>
      <Row>
        <Col as='div' className='all-center'>
          <Card.Title>Election name: {name}</Card.Title>
          <Card.Body>
            <Card.Text>Active: {votation.active.toString()}</Card.Text>
            <Card.Text>Voted: {votation.hasVoted.toString()}</Card.Text>
          </Card.Body>
        </Col>
        <Col as='div' className='all-center'>
          <Form onSubmit={onSubmit}>
            <Row>
              <Col>
                <fieldset>
                  <Form.Group controlId='voteForm'>
                    <Form.Label as='h5'>Candidates</Form.Label>
                    {votation.candidates.map((candidate) => (
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
  ) : null;
};

export default Votation;
