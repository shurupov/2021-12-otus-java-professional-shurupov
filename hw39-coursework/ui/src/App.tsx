import {Breadcrumb, Layout, Menu} from 'antd';
import 'antd/dist/antd.css';
import './utils/layout.css';
import React from "react";
import {Provider} from "react-redux";
import {history, store} from "store/store";
import { ConnectedApartmentList } from 'components/ApartmentList/ConnectedApartmentList';
import {ConnectedRouter} from "connected-react-router";
import {Link, Redirect, Route, Switch} from 'react-router-dom';
import {ConnectedSignIn} from "components/Cabinet/SignInForm/ConnectedSignIn";
import {ConnectedSignUp} from "components/SignUpForm/ConnectedSignUp";
import { ConnectedUserMenu } from 'components/Cabinet/ConnectedUserMenu';

export const App = () => {

    return (
        <Provider store={store}>
            <ConnectedRouter history={history}>
                <Layout className="layout">
                    <Layout.Header>
                        <div className="logo" />
                        <Menu
                            theme="dark"
                            mode="horizontal"
                            style={{ float: "left" }}
                        >
                            <Link component={Menu.Item} to="/apartments" style={{ float: "right", width: 140 }}>Список квартир</Link>
                        </Menu>
                        <ConnectedUserMenu />
                    </Layout.Header>
                    <Layout.Content className="content-container">
                        <Breadcrumb style={{ margin: '16px 0' }}>
                            <Link component={Breadcrumb.Item}  to="/">Home</Link>
                            <Link component={Breadcrumb.Item} to="/apartments">Apartments</Link>
                        </Breadcrumb>
                        <div className="content">
                            <Switch>
                                <Route path="/apartments" exact>
                                    <ConnectedApartmentList />
                                </Route>
                                <Route path="/signin" exact>
                                    <ConnectedSignIn />
                                </Route>
                                <Route path="/signup/:hash" >
                                    <ConnectedSignUp />
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