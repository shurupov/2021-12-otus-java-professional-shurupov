import {createSlice} from "@reduxjs/toolkit";

export const apartmentsSlice = createSlice({
    name: "apartments",
    initialState: {
        list: []
    },
    reducers: {
        list: (state, action) => {
            return {
                ...state,
                list: action.payload
            }
        }
    }
});