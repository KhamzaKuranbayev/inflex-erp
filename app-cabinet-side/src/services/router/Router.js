import React, { Component } from "react";
import {
  BrowserRouter as WebRouter,
  Redirect,
  Route,
  Switch,
} from "react-router-dom";
import history from "./history";
import AuthLoader from "../auth/context/AuthLoader";
import LayoutManager from "../../views/layouts/LayoutManager";
import IsAuth from "../auth/context/IsAuth";
import IsGuest from "../auth/context/IsGuest";
// auth pages
import LoginPage from "../../modules/auth/views/pages/LoginPage";
import { Dashboard } from "../../layout/dashboard/Dashboard";
// !auth pages

class Router extends Component {
  render() {
    return (
      <WebRouter history={history}>
        <AuthLoader>
          <LayoutManager>
            <IsAuth>
              <Switch>
                <Route exact path="/" component={"Home"} />
                <Redirect to="/" />
              </Switch>
            </IsAuth>
            <IsGuest>
              <Switch>
                <Route exact path="/" component={Dashboard} />
                <Route exact path="/auth" component={LoginPage} />
                <Redirect to="/auth" />
              </Switch>
            </IsGuest>
          </LayoutManager>
        </AuthLoader>
      </WebRouter>
    );
  }
}

export default Router;
