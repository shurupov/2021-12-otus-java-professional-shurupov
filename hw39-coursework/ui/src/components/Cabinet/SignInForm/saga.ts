import {sagaActionTypes} from "store/sagaActionTypes";
import {call, put, takeEvery, delay, select} from "redux-saga/effects";
import {authenticatedSelector, extendedFetch} from "utils/auth";
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

export function* workerSignIn(action:AnyAction): any {
    try {
        const jwtResponse = yield call(loginFetch, action.payload);
        console.log("workerSignIn jwtResponse", jwtResponse);
        yield put(cabinetSlice.actions.signIn(jwtResponse));
        const authenticated: boolean = yield select(authenticatedSelector);
        console.log("workerSignIn authenticated " + authenticated);
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