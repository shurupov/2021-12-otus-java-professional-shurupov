import {Button, Form, Input, Typography} from "antd";
import React from "react";

export interface SignInRequest {
    username: string;
    password: string;
}

interface AuthProps {
    submit: (values: SignInRequest) => void;
}

export const SignIn = (props: AuthProps) => {

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <Form
            name="basic"
            labelCol={{ span: 9 }}
            wrapperCol={{ span: 7 }}
            initialValues={{ remember: true }}
            onFinish={props.submit}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
        >

            <Typography.Title level={2} style={{ textAlign: "center" }}>Войти в систему</Typography.Title>

            <Form.Item
                label="Имя пользователя"
                name="username"
                rules={[{ required: true, message: 'Please input your username!' }]}

            >
                <Input />
            </Form.Item>

            <Form.Item
                label="Пароль"
                name="password"
                rules={[{ required: true, message: 'Please input your password!' }]}
            >
                <Input.Password />
            </Form.Item>

            {/*<Form.Item name="remember" valuePropName="checked" wrapperCol={{ offset: 9, span: 7 }}>
                <Checkbox>Remember me</Checkbox>
            </Form.Item>*/}

            <Form.Item wrapperCol={{ offset: 9, span: 7 }}>
                <Button type="primary" htmlType="submit">
                    Войти
                </Button>
            </Form.Item>
        </Form>
    );
}