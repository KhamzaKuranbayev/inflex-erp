import React from "react";
import ReactDOM from "react-dom";
import Router from "./services/router";
import Store from "./services/store";
import reportWebVitals from "./services/web-vitals";
import Auth from "./services/auth/context/Auth";
import "./assets/css/index.css";
import "./assets/css/style.scss";

ReactDOM.render(
  <React.StrictMode>
    <Store>
      <Auth>
        <Router />
      </Auth>
    </Store>
  </React.StrictMode>,
  document.getElementById("root")
);

reportWebVitals();
