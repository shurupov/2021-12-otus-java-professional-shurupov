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
    const apartments = yield call(authenticatedFetch, "/core/api/apartments");
    apartments.forEach((a: any) => a.key = a.id);
    console.log(apartments);
    yield put(apartmentsSlice.actions.list(apartments))
}

export function* watchApartmentList() {
    yield takeEvery(sagaActionTypes.APARTMENTS, workerApartmentList)
}