import React, { Fragment } from 'react';
import Form from 'react-bootstrap/Form';
import FormGroup from 'react-bootstrap/FormGroup';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';

const KeyForm = () => {
  const onVerify = e => {};

  return (
    <Container>
      <Form>
        <FormGroup controlId='formPrivateKey' style='width: '>
          <Form.Label>Enter private key to join voting</Form.Label>
          <Form.Control type='text' placeholder='private key' ref='' />
        </FormGroup>
        <Button variant='primary' type='submit'>
          Verify
        </Button>
      </Form>
    </Container>
  );
};

export default KeyForm;
