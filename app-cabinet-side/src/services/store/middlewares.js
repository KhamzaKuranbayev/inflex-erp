import createSagaMiddleware from "redux-saga";
import { applyMiddleware } from "redux";
import sagas from "./sagas";
import axios from "axios";
import storage from "../storage";
import get from "lodash/get";

const sagaMiddleware = createSagaMiddleware();

const list = [sagaMiddleware];

const apply = applyMiddleware(...list);

const afterCreate = (store) => {
  sagaMiddleware.run(sagas);
  store.subscribe(() => {
    let state = store.getState();
    var tokenStorage = storage.get("token");
    var tokenState = storage.get("token");
    if (get(state, "auth.token", false)) {
      axios.defaults.headers["Authorization"] = `Bearer ${tokenState}`;
      storage.set("token", get(state, "auth.token"));
    } else if (tokenStorage) {
      axios.defaults.headers["Authorization"] = `Bearer ${tokenStorage}`;
    } else {
      axios.defaults.headers.common["Authorization"] = "";
    }
  });
  return store;
};

export { apply, afterCreate, list };
