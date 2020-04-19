import React, { useContext } from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import SignIn from './components/pages/SignIn';
import MyNavbar from './components/layout/MyNavbar';
import Votations from './components/votations/Votations';
import Votation from './components/votations/Votation';
import KeyState from './components/context/key/keyState';
import KeyContext from './components/context/key/keyContext';

const AuthenticatedRoute = ({ component: Component, ...rest }) => {
  const { key } = useContext(KeyContext);
  return (
    <Route {...rest} render={(props) => key === '' ?
      (<Redirect to='/sign-in' />) :
      <Component {...props} />
    }
    />
  )
};

const App = () => {
  return (
    <KeyState>
      <Router>
        <div className='app'>
          <MyNavbar />
          <Container>
            <Switch>
              <Route exact path='/sign-in' component={SignIn} />
              <AuthenticatedRoute exact path='/votations' component={Votations} />
              <AuthenticatedRoute exact path='/votations/:name' component={Votation} />
              <Route path='/'>
                <Redirect to='/votations' />
              </Route>
            </Switch>
          </Container>
        </div>
      </Router>
    </KeyState>

  );
};

export default App;
