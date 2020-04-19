import React, { useContext, useState } from 'react';
import { withRouter } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';

import KeyContext from 'components/context/key/keyContext';
const { Secp256k1PrivateKey } = require('sawtooth-sdk/signing/secp256k1');


const validateKey = key => {
  try {
    Secp256k1PrivateKey.fromHex(key);
  } catch (e) {
    return false;
  }
  return true;
}
const KeyForm = (props) => {
  const keyContext = useContext(KeyContext);
  const [key, setKey] = useState('');
  const [keyError, setKeyError] = useState(false);

  const onSubmit = (e) => {
    e.preventDefault();
    const isValid = validateKey(key);
    if (!isValid) {
      e.stopPropagation();
      setKeyError(true);
      return false;
    }
    setKeyError(false);
    keyContext.setKey(key);
    props.history.push('/');
  };

  return (
    <Form onSubmit={onSubmit}>
      <Form.Group controlId='formPrivateKey'>
        <InputGroup>
          <FormControl
            type="text"
            placeholder='Your private key'
            value={key}
            onChange={e => setKey(e.target.value)}
            isInvalid={keyError}
          />
          <InputGroup.Append>
            <Button variant='primary' type='submit'>
              Sign in
            </Button>
          </InputGroup.Append>
          <Form.Control.Feedback type='invalid'>
            The key you provided is invalid. Please try again.
          </Form.Control.Feedback>
        </InputGroup>
      </Form.Group>
    </Form>
  );
};

export default withRouter(KeyForm);
