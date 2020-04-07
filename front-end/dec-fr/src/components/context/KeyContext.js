import React, { useState } from 'react';

const KeyContext = React.createContext([{}, () => {}]);

const KeyProvider = (props) => {
  const [state, setState] = useState({});

  return (
    <KeyContext.Provider value={[state, setState]}>
      {props.children}
    </KeyContext.Provider>
  );
};

export { KeyContext, KeyProvider };
