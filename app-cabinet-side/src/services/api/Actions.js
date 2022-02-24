import {createRoutine} from "redux-saga-routines";

const GET_ALL = createRoutine("GET_ALL");
const GET_ONE = createRoutine("GET_ONE");

// eslint-disable-next-line import/no-anonymous-default-export
export default {
    GET_ALL,
    GET_ONE
}