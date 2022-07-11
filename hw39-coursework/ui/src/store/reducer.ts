import { connectRouter } from "connected-react-router";
import { combineReducers } from "redux";
import {apartmentsSlice} from "components/ApartmentList/slice";

export const createRootReducer = (history: any) => combineReducers({
    router: connectRouter(history),
    apartments: apartmentsSlice.reducer
});