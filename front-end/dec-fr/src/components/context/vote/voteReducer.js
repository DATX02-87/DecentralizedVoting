import {
  GET_ELIGIBLE_ELECTIONS_FROM_API,
  CAST_VOTE_ON_API,
  SET_LOADING,
} from '../types';

export default (state, action) => {
  switch (action.type) {
    case GET_ELIGIBLE_ELECTIONS_FROM_API:
      return {
        ...state,
        votations: action.payload,
        loading: false,
      };
    case CAST_VOTE_ON_API:
      return {
        ...state,
        vote: action.payload,
        loading: false,
      };
    case SET_LOADING:
      return {
        ...state,
        loading: true,
      };
    default:
      return state;
  }
};
