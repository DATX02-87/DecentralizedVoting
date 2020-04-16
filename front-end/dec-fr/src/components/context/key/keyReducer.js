import { SET_KEY } from '../types';

export default (state, action) => {
  switch (action.type) {
    case SET_KEY:
      return action.payload;
    default:
      return state;
  }
};
