import {sagaActionTypes} from "store/sagaActionTypes";
import {call, put, takeEvery} from "redux-saga/effects";
import {extendedFetch} from "utils/auth";
import {AnyAction} from "redux";
import {SignInRequest} from "components/Cabinet/SignInForm/SignIn";
import { push } from "connected-react-router";
import {cabinetSlice} from "components/Cabinet/slice";

export const signInAction = (signInRequest: SignInRequest) => {
    return {
        type: sagaActionTypes.AUTHENTICATION_SIGNIN,
        payload: signInRequest
    }
}

export const signInLocalstorage = () => {
    return {
        type: sagaActionTypes.AUTHENTICATION_SIGNIN_STORAGE,
    }
}

export function* workerSignIn(action:AnyAction): any {
    try {
        const jwtResponse = yield call(loginFetch, action.payload);
        yield put(cabinetSlice.actions.signIn(jwtResponse));
        yield put(push("/"));
    } catch (e: any) {
        if (e.name == "BadResponse" && e.response.status == 401) {
            console.log("Ошибка аутентификации");
        }
    }
}

export function* workerSignInLocalstorage(): any {
    try {

        const jwttoken = localStorage.getItem("jwttoken");

        if (!jwttoken) {
            return;
        }

        const authHeaders = {
            "Authorization": "Bearer " + jwttoken
        };

        const jwtResponse = yield call(extendedFetch, "/core/api/auth/refresh", "POST", null, authHeaders);
        yield put(cabinetSlice.actions.signIn(jwtResponse));
        yield put(push("/"));
    } catch (e: any) {
        if (e.name == "BadResponse" && e.response.status == 401) {
            console.log("Ошибка аутентификации");
        }
    }
}

export function* loginFetch(authRequest: SignInRequest): any {
    return yield call(extendedFetch, "/core/api/auth/signin", "POST", authRequest);
}

export function* watchSignIn() {
    yield takeEvery(sagaActionTypes.AUTHENTICATION_SIGNIN, workerSignIn);
}

export function* watchSignInLocalstorage() {
    yield takeEvery(sagaActionTypes.AUTHENTICATION_SIGNIN_STORAGE, workerSignInLocalstorage);
}