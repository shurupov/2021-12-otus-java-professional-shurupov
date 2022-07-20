import {apartmentListAction} from "components/ApartmentList/saga";
import {put, select, takeEvery} from "redux-saga/effects";
import {sagaActionTypes} from "store/sagaActionTypes";
import {push} from "connected-react-router";
import {signUpSlice} from "components/SignUpForm/slice";
import {authenticated} from "utils/auth";

export const pathSelector = (state: any) => state.router.location.pathname;

export function* workerLocationChange(): any {
    const url: string = yield select(pathSelector);

    if (url == "/apartments") {
        yield put(apartmentListAction());
    } else if (url.substring(0, 8) == "/signup/") {
        yield put(signUpSlice.actions.signUp({ hash: url.substring(8)}));
    } /*else if (url == "/") {
        if (authenticated()) {
            push("/apartments")
        } else {
            push("/signin");
        }
    }*/
}

export function* watchLocationChange() {
    yield takeEvery(sagaActionTypes.LOCATION_CHANGE, workerLocationChange);
}