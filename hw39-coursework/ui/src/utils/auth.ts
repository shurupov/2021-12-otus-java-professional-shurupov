import {call, put} from "redux-saga/effects";
import {SignInRequest} from "components/SignInForm/SignIn";
import { push } from "connected-react-router";
import {store} from "store/store";

export function* extendedFetch(url: string, method = "GET", body: any = undefined, headers = {}): any {
    const requestSettings: RequestInit = {
        method,
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            ...headers
        },
        body: body ? JSON.stringify(body) : undefined,
    };
    const response: Response = yield call(fetch, url, requestSettings);
    if (!response.ok) {
        throw {
            message: "Request exception",
            response,
        };
    }
    return yield call([response, 'json']);
}

export function* loginFetch(authRequest: SignInRequest): any {
    return yield call(extendedFetch, "/core/api/auth/signin", "POST", authRequest);
}

export function* authenticatedFetch(url: string, method = "GET", body: any = undefined): any {
    if (!authenticated()) {
        console.log("not authenticated");
        yield put(push("/signin"));
        return;
    }
    const jwttoken = localStorage.getItem("jwttoken");
    const authHeaders = {
        "Authorization": "Bearer " + jwttoken
    };

    try {
        return yield call(extendedFetch, url, method, body, authHeaders);
    } catch (e: any) {
        if (e.response != undefined && e.response.status == 401) {
            console.log("not authenticated");
            yield put(push("/signin"));
        }
    }
}

export const authenticated = (): boolean => {
    return localStorage.getItem("jwttoken") != null;
}

export const logout = (): void => {
    localStorage.removeItem("jwttoken");
    store.dispatch(push("/signin"));
    /*setTimeout(() => {

    }, 500);*/
}