import React, { useReducer } from 'react';
import KeyReducer from './keyReducer';
import KeyContext from './keyContext';
import { SET_KEY } from '../types';

const KeyState = (props) => {
  const initialState = {
    key: '',
  };

  const [state, dispatch] = useReducer(KeyReducer, initialState);

  const setKey = (key) => {
    dispatch({
      type: SET_KEY,
      payload: key,
    });
  };

  return (
    <KeyContext.Provider value={{ key: state, setKey }}>
      {props.children}
    </KeyContext.Provider>
  );
};

export default KeyState;
