import {Button, Form, Input} from "antd";
import React from "react";
import { SignUpResult } from "./SignUpResult";

export interface SignUpRequest {
    hash: string;
    username: string;
    password: string;
}

interface SignUpProps {
    done: boolean;
    hash: string;
    message: string;
    submit: (values: SignUpRequest) => void;
}

export const SignUp = (props: SignUpProps) => {

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    const submit = (hash: string) => {
        return (form: any) => {
            props.submit({
                ...form,
                hash
            });
        }
    }

    if (props.done) {
        return <SignUpResult status="success" message={props.message} />;
    }

    return (
        <Form
            name="basic"
            labelCol={{ span: 9 }}
            wrapperCol={{ span: 7 }}
            initialValues={{ remember: true }}
            onFinish={submit(props.hash)}
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

            <Form.Item wrapperCol={{ offset: 9, span: 7 }}>
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
}