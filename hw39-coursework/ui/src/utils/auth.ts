import {call} from "redux-saga/effects";
import {history} from "store/store";
import {SignInRequest} from "components/SignInForm/SignIn";

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
            name: "BadResponse",
            message: "Bad Response",
            response: response,
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
        history.push("/signin");
    }
    const jwttoken = localStorage.getItem("jwttoken");
    console.log(jwttoken);
    const authHeaders = {
        "Authorization": "Bearer " + jwttoken
    };

    return yield call(extendedFetch, url, method, body, authHeaders);
}

export const authenticated = (): boolean => {
    return localStorage.getItem("jwttoken") != null;
}

export const logout = (): void => {
    localStorage.removeItem("jwttoken");
    setTimeout(() => {
        history.push("/signin");
    }, 500);
}