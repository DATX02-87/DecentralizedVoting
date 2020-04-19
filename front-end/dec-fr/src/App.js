import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Home from './components/pages/Home';
import MyNavbar from './components/layout/MyNavbar';
import Votations from './components/votations/Votations';
import Votation from './components/votations/Votation';
import KeyState from './components/context/key/keyState';
import VoteState from './components/context/vote/voteState';

const App = () => {
  return (
    <VoteState>
      <KeyState>
        <Router>
          <div className='app'>
            <MyNavbar />
            <Container>
              <Switch>
                <Route exact path='/' component={Home} />
                <Route exact path='/votations' component={Votations} />
                <Route exact path='/votation/:name' component={Votation} />
              </Switch>
            </Container>
          </div>
        </Router>
      </KeyState>
    </VoteState>
  );
};

export default App;
