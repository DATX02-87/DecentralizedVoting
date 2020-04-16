import React, { useContext } from 'react';
import Form from 'react-bootstrap/Form';
import FormGroup from 'react-bootstrap/FormGroup';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import '../../App.css';
// import KeyContext from '../context/key/keyContext';
import VoteContext from '../context/vote/voteContext';
import KeyContext from '../context/key/keyContext';

const KeyForm = () => {
  // const keyContext = useContext(KeyContext);
  const voteContext = useContext(VoteContext);
  const keyContext = useContext(KeyContext);
  const { key, setKey } = keyContext;

  const onChange = (e) => setKey(e.target.value);

  const onSubmit = (e) => {
    e.preventDefault();
    if (key === '') {
      alert('enter a key');
    } else {
      voteContext.getEligibleElectionsFromApi(key);
    }
  };

  return (
    <Card border='light' className='card'>
      <Form onSubmit={onSubmit}>
        <FormGroup controlId='formPrivateKey'>
          <Card.Title>Log in to find elections</Card.Title>
          <Form.Label></Form.Label>
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

export default KeyForm;
