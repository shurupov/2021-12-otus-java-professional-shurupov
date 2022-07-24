import {Dispatch} from "redux";
import {connect} from "react-redux";
import {UserMenu} from "components/Cabinet/UserMenu";
import {cabinetSlice} from "components/Cabinet/slice";
import {push} from "connected-react-router";

const mapStateToProps = (storeState: any) => {

    return {
        ...storeState.cabinet
    }
}

const mapDispatchToProps = (dispatch: Dispatch) => {
    return {
        signIn: () => {
            dispatch(push("/signin"));
        },
        logout: () => {
            dispatch(cabinetSlice.actions.logout());
            dispatch(push("/"));
        },
    }
};

export const ConnectedUserMenu = connect(mapStateToProps, mapDispatchToProps)(UserMenu);
