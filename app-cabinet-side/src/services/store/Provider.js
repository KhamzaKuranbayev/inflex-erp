import React from "react";
import { Provider } from "react-redux";
import configure from "./configure";

const store = configure();

// eslint-disable-next-line import/no-anonymous-default-export
export default ({ children }) => {
  return <Provider store={store}>{children}</Provider>;
};
