import React from "react"
import {Button, Result} from "antd";
import {ResultStatusType} from "antd/lib/result";
import {Link} from "react-router-dom";

export interface SignUpResultProps {
    status: ResultStatusType;
    message: string;
}

export const SignUpResult = (props: SignUpResultProps) => {
    return (
        <Result
            status={props.status}
            title={props.message}
            extra={[
                <Link to="/signin">
                    <Button type="primary">Войти в систему</Button>
                </Link>
            ]}
        />
    );
}