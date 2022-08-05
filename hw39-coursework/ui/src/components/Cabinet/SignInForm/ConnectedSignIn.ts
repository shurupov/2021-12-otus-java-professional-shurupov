import {connect} from "react-redux";
import {Dispatch} from "redux";
import {SignInRequest, SignIn} from "components/Cabinet/SignInForm/SignIn";
import {signInAction} from "components/Cabinet/SignInForm/saga";

const mapDispatchToProps = (dispatch: Dispatch) => {
    return {
        submit: (authRequest: SignInRequest) => {
            dispatch(signInAction(authRequest));
        }
    }
};

export const ConnectedSignIn = connect(null, mapDispatchToProps)(SignIn);