import React from "react";
import Card from 'react-bootstrap/Card';
import KeyForm from "./KeyForm";

const SignIn = () => (
  <Card className='mt-4'>
    <Card.Body>
      <Card.Title>Welcome to the voting system</Card.Title>
      <Card.Text>
        Please input your private key in the field below and click "Sign in" to use the system.
      </Card.Text>
      <KeyForm/>
    </Card.Body>
  </Card>
);

export default SignIn;
