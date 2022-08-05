import {sagaActionTypes} from "store/sagaActionTypes";
import {SignUpRequest} from "components/SignUpForm/SignUp";
import {call, put, takeEvery} from "redux-saga/effects";
import {AnyAction} from "redux";
import {extendedFetch} from "utils/auth";
import {signUpSlice} from "components/SignUpForm/slice";

export const signUpAction = (signUpRequest: SignUpRequest) => {
    return {
        type: sagaActionTypes.AUTHENTICATION_SIGNUP,
        payload: signUpRequest
    }
}

export function* workerSignUp(action:AnyAction): any {
    try {
        const signUpResponse: any = yield call(extendedFetch, "/core/api/auth/signup", "POST", action.payload);
        yield put(signUpSlice.actions.done({ message: signUpResponse.message }));
    } catch (e: any) {
        if (e.name == "BadResponse" && e.response.status == 401) {
            console.log("Ошибка аутентификации");
        }
    }
}

export function* watchSignUp() {
    yield takeEvery(sagaActionTypes.AUTHENTICATION_SIGNUP, workerSignUp);
}