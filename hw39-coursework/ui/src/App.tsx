import {Breadcrumb, Layout} from 'antd';
import 'antd/dist/antd.css';
import './utils/layout.css';
import React from "react";
import {Apartment} from "components/ApartmentList/ApartmentList";
import {Provider} from "react-redux";
import {history, store} from "store/store";
import { ConnectedApartmentList } from 'components/ApartmentList/ConnectedApartmentList';
import {ConnectedRouter} from "connected-react-router";
import {Redirect, Route, Switch} from 'react-router-dom';
import {SignIn} from "components/SignInForm/SignIn";
import {ConnectedSignIn} from "components/SignInForm/ConnectedSignIn";

const apartments: Array<Apartment> = [
    {
        id: 1,
        apartmentType: { id: 1, name: "Квартира"},
        number: 1,
        floor: 1,
        square: 50.5
    },
    {
        id: 2,
        apartmentType: { id: 1, name: "Квартира"},
        number: 2,
        floor: 1,
        square: 70
    },
    {
        id: 3,
        apartmentType: { id: 1, name: "Квартира"},
        number: 3,
        floor: 2,
        square: 50.5
    },
    {
        id: 4,
        apartmentType: { id: 1, name: "Квартира"},
        number: 4,
        floor: 2,
        square: 70
    },
];

export const App = () => {
    return (
        <Provider store={store}>
            <ConnectedRouter history={history}>
                <Layout className="layout">
                    <Layout.Header>
                        <div className="logo" />
                    </Layout.Header>
                    <Layout.Content className="content-container">
                        <Breadcrumb style={{ margin: '16px 0' }}>
                            <Breadcrumb.Item>Home</Breadcrumb.Item>
                            <Breadcrumb.Item>List</Breadcrumb.Item>
                            <Breadcrumb.Item>App</Breadcrumb.Item>
                        </Breadcrumb>
                        <div className="content">
                            <Switch>
                                <Route path="/" exact>
                                    <Redirect to="/apartments" />
                                </Route>
                                <Route path="/apartments" exact>
                                    <ConnectedApartmentList />
                                </Route>
                                <Route path="/signin" exact>
                                    <ConnectedSignIn />
                                </Route>
                            </Switch>
                        </div>
                    </Layout.Content>
                    <Layout.Footer style={{ textAlign: 'center' }}>Home owners (<a target="_blank" href="https://t.me/eshurupov">Евгений Шурупов</a>)</Layout.Footer>
                </Layout>
            </ConnectedRouter>
        </Provider>
    );
}