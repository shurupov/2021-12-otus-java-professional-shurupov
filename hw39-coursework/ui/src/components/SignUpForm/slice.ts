import {createSlice} from "@reduxjs/toolkit";

export const signUpSlice = createSlice({
    name: "signUp",
    initialState: {
        hash: "",
        done: true,
        message: ""
    },
    reducers: {
        signUp: (state, action) => {
            return {
                ...state,
                done: false,
                hash: action.payload.hash,
                message: "",
            }
        },
        done: (state, action) => {
            return {
                ...state,
                done: true,
                hash: "",
                message: action.payload.message,
            }
        }
    }
});