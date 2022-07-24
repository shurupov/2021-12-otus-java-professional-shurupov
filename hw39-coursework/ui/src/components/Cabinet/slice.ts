import {createSlice} from "@reduxjs/toolkit";

export const cabinetSlice = createSlice({
    name: "cabinet",
    initialState: {
        authenticated: false,
    },
    reducers: {
        signIn: (state, action) => {
            localStorage.setItem("jwttoken", action.payload.token);
            console.log("cabinet.signIn action: ", action);
            return {
                ...action.payload,
                authenticated: true,
            }
        },
        logout: (state, action) => {
            return {
                authenticated: false
            }
        }
    }
});