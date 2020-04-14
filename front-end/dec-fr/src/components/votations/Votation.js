import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import { getElection } from '../../services/api';
import { KeyContext } from '../context/KeyContext';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Row } from 'react-bootstrap';

const Votation = () => {
  const keyContext = useContext(KeyContext);
  const { key } = keyContext;
  const { name } = useParams();

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
      <Container>
        <Row>
          <Col>
            <Card.Title>Election name: {name}</Card.Title>
            <Card.Body>
              <Card.Text>Active: {votation.active.toString()}</Card.Text>
              <Card.Text>Voted: {votation.hasVoted.toString()}</Card.Text>
            </Card.Body>
          </Col>
          <Col>
            <Form>
              <Form.Label as='h5'>Candidates</Form.Label>
              <fieldset>
                <Form.Group controlId='voteForm'>
                  {votation.candidates.map((candidate) => (
                    <div key={candidate} className='mb-3'>
                      <Form.Check
                        type='radio'
                        name='voteForm'
                        id={candidate}
                        label={candidate}
                      />
                    </div>
                  ))}
                </Form.Group>
              </fieldset>
              <Button type='submit'>Submit</Button>
            </Form>
          </Col>
        </Row>
      </Container>
    </Card>
  ) : null;
};

export default Votation;
