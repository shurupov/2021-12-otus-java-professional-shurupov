import {Avatar, Menu} from "antd";
import React from "react";
import {Link} from "react-router-dom";

export interface UserMenuProps {
    authenticated: boolean;
    logout: Function;
}

/*export interface UserApartment extends Apartment {
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
}*/

export const UserMenu = (props: any) => {
    if (!props.authenticated) {
        return (
            <Menu
                theme="dark"
                mode="horizontal"
                style={{ float: "right", width: 100 }}
            >
                <Menu.Item onClick={props.signIn}>
                    Войти
                </Menu.Item>
            </Menu>
        );
    }

    return (
        <Menu
            theme="dark"
            mode="horizontal"
            style={{ float: "right", width: 128 }}
        >
            <Menu.Item onClick={props.logout}>
                <Avatar src="https://joeschmoe.io/api/v1/random" /> Выход
            </Menu.Item>
        </Menu>
    );
}