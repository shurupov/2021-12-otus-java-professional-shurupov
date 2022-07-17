import {Button, Checkbox, Form, Input} from "antd";
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
            <Form.Item
                label="Username"
                name="username"
                rules={[{ required: true, message: 'Please input your username!' }]}

            >
                <Input />
            </Form.Item>

            <Form.Item
                label="Password"
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
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
}