import {Dispatch} from "redux";
import {connect} from "react-redux";
import {UserMenu} from "components/Cabinet/UserMenu";

const mapStateToProps = (storeState: any) => {

    return {
        ...storeState.cabinet
    }
}

const mapDispatchToProps = (dispatch: Dispatch) => {
    return {
        submit: (some: any) => {
            // dispatch(signInAction(authRequest));
        }
    }
};

export const ConnectedUserMenu = connect(mapStateToProps)(UserMenu);
