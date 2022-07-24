import {Avatar, Menu} from "antd";
import {logout} from "utils/auth";
import React from "react";
import {Apartment} from "components/ApartmentList/ApartmentList";
import {Link} from "react-router-dom";

export interface UserMenuProps extends SignInResponse {
    authenticated: boolean;
}

export interface UserApartment extends Apartment {
    share: number;
    decisionMaker: boolean;
    ownerShipType: string;
}

export interface UserBuilding {
    id: number;
    address: string;
    apartments: Array<UserApartment>;
}

export interface SignInResponse {
    id: number;
    fullName: string;
    shortName: string;
    token: string;
    username: string;
    roles: Array<string>;
    userBuildings: Array<UserBuilding>;
}

export const UserMenu = (props: any) => {
    if (!props.authenticated) {
        return (
            <Menu
                theme="dark"
                mode="horizontal"
                style={{ float: "right" }}
            >
                <Link component={Menu.Item} to="/signin">Войти</Link>
            </Menu>
        );
    }

    return (
        <Menu
            theme="dark"
            mode="horizontal"
            style={{ float: "right" }}
        >
            <Menu.Item onClick={() => logout()}><Avatar src="https://joeschmoe.io/api/v1/random" /> Выход</Menu.Item>
        </Menu>
    );
}