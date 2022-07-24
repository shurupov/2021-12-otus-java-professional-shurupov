import {createBrowserHistory} from "history";
import createSagaMiddleware from "redux-saga";
import {createRootReducer} from "store/reducer";
import { applyMiddleware, compose, createStore } from 'redux'
import { PreloadedState, Store } from "@reduxjs/toolkit";
import {watchApartmentList} from "components/ApartmentList/saga";
import {composeWithDevTools} from "redux-devtools-extension";
import {watchLocationChange} from "components/routing/saga";
import {routerMiddleware} from "connected-react-router";
import {watchSignIn} from "components/Cabinet/SignInForm/saga";
import {watchSignUp} from "components/SignUpForm/saga";

export const history = createBrowserHistory();

const sagaMiddleware = createSagaMiddleware();

export default function configureStore(preloadedState: PreloadedState<any>) {
    const store = createStore(
        createRootReducer(history), // root reducer with router state
        preloadedState,
        compose(
            composeWithDevTools(
                applyMiddleware(
                    routerMiddleware(history),
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
sagaMiddleware.run(watchSignIn);
sagaMiddleware.run(watchSignUp);