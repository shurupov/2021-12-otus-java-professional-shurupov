import {sagaActionTypes} from "store/sagaActionTypes";
import {call, takeEvery} from "redux-saga/effects";
import {loginFetch} from "utils/auth";
import {AnyAction} from "redux";
import {history} from "store/store";
import {SignInRequest} from "components/SignInForm/SignIn";

export const signInAction = (signInRequest: SignInRequest) => {
    return {
        type: sagaActionTypes.AUTHENTICATION_LOGIN,
        payload: signInRequest
    }
}

export function* workerSignIn(action:AnyAction): any {
    try {
        console.log(action);
        const jwtResponse = yield call(loginFetch, action.payload);
        localStorage.setItem("jwttoken", jwtResponse.token);
        history.push("/");
    } catch (e: any) {
        if (e.name == "BadResponse" && e.response.status == 401) {
            console.log("Ошибка аутентификации");
        }
    }
}

export function* watchSignIn() {
    yield takeEvery(sagaActionTypes.AUTHENTICATION_LOGIN, workerSignIn);
}