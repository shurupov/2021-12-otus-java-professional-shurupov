import {apartmentListAction} from "components/ApartmentList/saga";
import {put, select, takeEvery} from "redux-saga/effects";
import {sagaActionTypes} from "store/sagaActionTypes";
import {history} from "store/store";

export const pathSelector = (state: any) => state.router.location.pathname;

export function* workerLocationChange(): any {
    const url = yield select(pathSelector);
    if (url == "/apartments") {
        yield put(apartmentListAction());
    } else if (url == "/") {
        history.push("/apartments")
    }
}

export function* watchLocationChange() {
    yield takeEvery(sagaActionTypes.LOCATION_CHANGE, workerLocationChange);
}