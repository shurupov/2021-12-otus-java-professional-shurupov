import {Breadcrumb, Layout, Menu} from 'antd';
import 'antd/dist/antd.css';
import './utils/layout.css';
import React from "react";
import {ApartmentList} from "components/ApartmentList/Apartments";

export const App = () => {
    return (
        <Layout className="layout">
            <Layout.Header>
                <div className="logo" />
                {/*<Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['2']}
                    items={new Array(15).fill(null).map((_, index) => {
                        const key = index + 1;
                        return {
                            key,
                            label: `nav ${key}`,
                        };
                    })}
                />*/}
            </Layout.Header>
            <Layout.Content className="content-container">
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                    <Breadcrumb.Item>App</Breadcrumb.Item>
                </Breadcrumb>
                <div className="content">
                    <ApartmentList apartments={[]} />
                </div>
            </Layout.Content>
            <Layout.Footer style={{ textAlign: 'center' }}>Home owners (<a target="_blank" href="https://t.me/eshurupov">Евгений Шурупов</a>)</Layout.Footer>
        </Layout>
    );
}