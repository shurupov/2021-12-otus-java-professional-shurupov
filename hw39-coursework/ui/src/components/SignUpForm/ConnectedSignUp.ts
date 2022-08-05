import {Dispatch} from "redux";
import {SignUp, SignUpRequest} from "components/SignUpForm/SignUp";
import {signUpAction} from "components/SignUpForm/saga";
import {connect} from "react-redux";

const mapStateToProps = (storeState: any) => {

    return {
        hash: storeState.signUp.hash,
        done: storeState.signUp.done,
        message: storeState.signUp.message,
    }
}

const mapDispatchToProps = (dispatch: Dispatch) => {
    return {
        submit: (authRequest: SignUpRequest) => {
            dispatch(signUpAction(authRequest));
        }
    }
};

export const ConnectedSignUp = connect(mapStateToProps, mapDispatchToProps)(SignUp);