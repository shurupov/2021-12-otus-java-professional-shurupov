import {call, put, takeEvery} from "redux-saga/effects";
import {sagaActionTypes} from "store/sagaActionTypes";
import {apartmentsSlice} from "components/ApartmentList/slice";
import {authenticatedFetch} from "utils/auth";

export const apartmentListAction = () => {
    return {
        type: sagaActionTypes.APARTMENTS
    };
}

export function* workerApartmentList(): any {
    console.log("workerApartmentList start")
    const apartments = yield call(authenticatedFetch, "/core/api/apartments");
    console.log("workerApartmentList got", apartments);
    apartments.forEach((a: any) => a.key = a.id);
    yield put(apartmentsSlice.actions.list(apartments));
}

export function* watchApartmentList() {
    yield takeEvery(sagaActionTypes.APARTMENTS, workerApartmentList)
}