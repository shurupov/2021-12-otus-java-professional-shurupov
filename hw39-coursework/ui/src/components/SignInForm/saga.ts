import {sagaActionTypes} from "store/sagaActionTypes";
import {call, put, takeEvery} from "redux-saga/effects";
import {loginFetch} from "utils/auth";
import {AnyAction} from "redux";
import {history} from "store/store";
import {SignInRequest} from "components/SignInForm/SignIn";
import { push } from "connected-react-router";

export const signInAction = (signInRequest: SignInRequest) => {
    return {
        type: sagaActionTypes.AUTHENTICATION_SIGNIN,
        payload: signInRequest
    }
}

export function* workerSignIn(action:AnyAction): any {
    try {
        const jwtResponse = yield call(loginFetch, action.payload);
        localStorage.setItem("jwttoken", jwtResponse.token);
        yield put(push("/"));
    } catch (e: any) {
        if (e.name == "BadResponse" && e.response.status == 401) {
            console.log("Ошибка аутентификации");
        }
    }
}

export function* watchSignIn() {
    yield takeEvery(sagaActionTypes.AUTHENTICATION_SIGNIN, workerSignIn);
}