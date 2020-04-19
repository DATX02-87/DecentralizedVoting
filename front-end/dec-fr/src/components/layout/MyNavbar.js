import React, { Fragment } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

const MyNavbar = () => {
  return (
    <Fragment>
      <Navbar bg='white' variant='light'>
        <Navbar.Brand>
          <Link to='/'>Decentralised Voting System</Link>
        </Navbar.Brand>
      </Navbar>
    </Fragment>
  );
};

MyNavbar.defaultProp = {
  icon: 'fas fa-link',
};

export default MyNavbar;
