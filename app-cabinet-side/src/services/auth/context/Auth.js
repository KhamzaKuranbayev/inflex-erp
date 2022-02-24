import React, { Component } from "react";
import get from "lodash/get";
import Provider from "./Provider";
import { connect } from "react-redux";
import Actions from "../actions";

class Auth extends Component {
  componentDidMount() {
    const { checkAuth } = this.props;
    checkAuth();
  }

  render() {
    const { children, isAuthenticated, isFetched, user } = this.props;
    return (
      <Provider
        value={{
          isAuthenticated,
          isFetched,
          user,
        }}
      >
        {children}
      </Provider>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    checkAuth: () =>
      dispatch({
        type: Actions.CHECK_AUTH.REQUEST,
      }),
  };
};

const mapStateToProps = (state, ownProps) => {
  return {
    ...ownProps,
    isAuthenticated: get(state.auth, "isAuthenticated", false),
    isFetched: get(state.auth, "isFetched", true),
    user: get(state.auth, "user", {}),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Auth);
