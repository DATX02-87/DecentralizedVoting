import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './components/pages/Home';
import MyNavbar from './components/layout/MyNavbar';
import Votations from './components/votations/Votations';
import Votation from './components/votations/Votation';
import { KeyProvider } from './components/context/KeyContext';

const App = () => {
  return (
    <KeyProvider>
      <Router>
        <div className='App'>
          <MyNavbar />
          <div className='container'>
            <Switch>
              <Route exact path='/' component={Home} />
              <Route exact path='/votations' component={Votations} />
              <Route exact path='/votation/:name' component={Votation} />
            </Switch>
          </div>
        </div>
      </Router>
    </KeyProvider>
  );
};

export default App;
