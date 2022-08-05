import { connectRouter } from "connected-react-router";
import { combineReducers } from "redux";
import {apartmentsSlice} from "components/ApartmentList/slice";
import {signUpSlice} from "components/SignUpForm/slice";
import {cabinetSlice} from "components/Cabinet/slice";

export const createRootReducer = (history: any) => combineReducers({
    router: connectRouter(history),
    apartments: apartmentsSlice.reducer,
    signUp: signUpSlice.reducer,
    cabinet: cabinetSlice.reducer
});