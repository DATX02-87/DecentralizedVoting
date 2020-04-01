import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './components/pages/Home';
import MyNavbar from './components/layout/MyNavbar';

const App = () => {
  return (
    <Router>
      <div className='App'>
        <MyNavbar />
        <div className='container'>
          <Switch>
            <Route exact path='/' component={Home} />
          </Switch>
        </div>
      </div>
    </Router>
  );
};

export default App;
