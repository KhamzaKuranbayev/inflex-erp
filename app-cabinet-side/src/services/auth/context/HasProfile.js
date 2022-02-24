import React, { Component } from "react";
import Consumer from "./Consumer";

class HasProfile extends Component {
  render() {
    const { children, fallback} = this.props;
    return (
      <Consumer>
        {({ isAuthenticated = false, profile = false }) => {
          return isAuthenticated && profile ? children : fallback;
        }}
      </Consumer>
    );
  }
}

export default HasProfile;
