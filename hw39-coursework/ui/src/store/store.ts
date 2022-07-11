import {createBrowserHistory} from "history";
import createSagaMiddleware from "redux-saga";
import {createRootReducer} from "store/reducer";
import { applyMiddleware, compose, createStore } from 'redux'
import { PreloadedState, Store } from "@reduxjs/toolkit";
import {watchApartmentList} from "components/ApartmentList/saga";
import {composeWithDevTools} from "redux-devtools-extension";
// import {routerMiddleware} from "connected-react-router";
import {watchLocationChange} from "components/routing/saga";

export const history = createBrowserHistory();

const sagaMiddleware = createSagaMiddleware();

export default function configureStore(preloadedState: PreloadedState<any>) {
    const store = createStore(
        createRootReducer(history), // root reducer with router state
        preloadedState,
        compose(
            composeWithDevTools(
                applyMiddleware(
                    // routerMiddleware(history),
                    sagaMiddleware
                )
            ),
        ),
    )

    return store;
}

export const store: Store = configureStore({});

sagaMiddleware.run(watchApartmentList);
sagaMiddleware.run(watchLocationChange);