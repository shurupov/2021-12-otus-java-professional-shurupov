import {call, put, select} from "redux-saga/effects";
import { push } from "connected-react-router";
import {store} from "store/store";

export const authenticatedSelector = (state: any) => state.cabinet.authenticated;
export const jwtTokenSelector = (state: any) => state.cabinet.token;

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

export function* authenticatedFetch(url: string, method = "GET", body: any = undefined): any {
    const authenticated: boolean = yield select(authenticatedSelector);
    console.log("authenticated " + authenticated);
    if (!authenticated) {
        console.log("not authenticated");
        yield put(push("/signin"));
        throw Error();
    }
    const jwttoken: string = yield select(jwtTokenSelector);
    console.log("jwttoken " + jwttoken);

    const authHeaders = {
        "Authorization": "Bearer " + jwttoken
    };

    try {
        return yield call(extendedFetch, url, method, body, authHeaders);
    } catch (e: any) {
        if (e.response != undefined && e.response.status == 401) {
            console.log(e, "not authenticated");
            yield put(push("/signin"));
        }
    }
}