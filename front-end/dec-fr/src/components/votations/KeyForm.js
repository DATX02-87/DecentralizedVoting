import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import FormGroup from 'react-bootstrap/FormGroup';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { withRouter } from 'react-router-dom';
import '../../App.css';

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
    <Card border='light' className='card'>
      <Form onSubmit={onSubmit}>
        <FormGroup controlId='formPrivateKey'>
          <Card.Title>Log in to find elections</Card.Title>
          <Form.Control
            size='sm'
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
    </Card>
  );
};

export default withRouter(KeyForm);
