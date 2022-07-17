import {connect} from "react-redux";
import {Dispatch} from "redux";
import {SignInRequest, SignIn} from "components/SignInForm/SignIn";
import {signInAction} from "components/SignInForm/saga";

const mapDispatchToProps = (dispatch: Dispatch) => {
    return {
        submit: (authRequest: SignInRequest) => {
            console.log(authRequest);
            dispatch(signInAction(authRequest));
        }
    }
};

export const ConnectedSignIn = connect(null, mapDispatchToProps)(SignIn);