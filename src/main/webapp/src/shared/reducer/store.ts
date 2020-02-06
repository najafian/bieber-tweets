import {createStore, applyMiddleware, compose} from 'redux';
import promiseMiddleware from 'redux-promise-middleware';
import thunkMiddleware from 'redux-thunk';
import reducer, {IRootState} from "./index";

const defaultMiddleWares = [
    thunkMiddleware,
    promiseMiddleware
];
const composedMiddleWares = (middleWares:any) => compose(applyMiddleware(...defaultMiddleWares, ...middleWares));

const initialize = (initialState?: IRootState, middleWares = [] as any[]) => createStore(reducer, initialState, composedMiddleWares(middleWares));

export default initialize;