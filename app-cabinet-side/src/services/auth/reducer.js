import Actions from "./Actions";

export default function AuthReducer(state = {}, action) {
  switch (action.type) {
    case Actions.CHECK_AUTH.REQUEST:
      return {
        ...state,
        user: null,
        isAuthenticated: false,
        isFetched: false,
      };
    case Actions.CHECK_AUTH.FAILURE:
      return {
        ...state,
        user: null,
        isAuthenticated: false,
        isFetched: true,
      };
    case Actions.CHECK_AUTH.SUCCESS:
      const { user } = action.payload;
      return {
        ...state,
        user,
        isAuthenticated: true,
        isFetched: true,
      };
    default:
      return state;
  }
}
