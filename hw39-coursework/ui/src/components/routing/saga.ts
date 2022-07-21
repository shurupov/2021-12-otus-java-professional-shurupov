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
        return;
    }
    if (url.substring(0, 8) == "/signup/") {
        yield put(signUpSlice.actions.signUp({ hash: url.substring(8)}));
        return;
    }
    if (url == "/") {
        yield put(push("/apartments"));
        return;
    }
}

export function* watchLocationChange() {
    yield takeEvery(sagaActionTypes.LOCATION_CHANGE, workerLocationChange);
}