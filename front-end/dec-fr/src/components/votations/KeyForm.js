import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import FormGroup from 'react-bootstrap/FormGroup';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import { withRouter } from 'react-router-dom';

const KeyForm = (props) => {
  const [key, setKey] = useState('');

  const onChange = (e) => setKey(e.target.value);

  const onSubmit = (e) => {
    e.preventDefault();
    if (key === '') {
      alert('enter a key');
    } else {
      props.history.push('/votations');
    }
  };

  return (
    <Container>
      <Form onSubmit={onSubmit}>
        <FormGroup controlId='formPrivateKey'>
          <Form.Label>Enter private key to join voting</Form.Label>
          <Form.Control
            type='text'
            placeholder='private key'
            value={key}
            onChange={onChange}
          />
        </FormGroup>
        <Button variant='primary' type='submit'>
          Submit
        </Button>
      </Form>
    </Container>
  );
};

export default withRouter(KeyForm);
