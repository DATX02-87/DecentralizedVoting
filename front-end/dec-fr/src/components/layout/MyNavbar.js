import React, { Fragment } from 'react';
import Navbar from 'react-bootstrap/Navbar';

const MyNavbar = () => {
  return (
    <Fragment>
      <Navbar bg='white' variant='light'>
        <Navbar.Brand href='/'>Decentralised Voting System</Navbar.Brand>
      </Navbar>
    </Fragment>
  );
};

MyNavbar.defaultProp = {
  icon: 'fas fa-link',
};

export default MyNavbar;
