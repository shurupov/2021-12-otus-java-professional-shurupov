import React from "react";
import {Table} from "antd";

export interface ApartmentType {
    id: number;
    name: string;
}

export interface Apartment {
    id: number;
    apartmentType: ApartmentType;
    number: number;
    floor: number;
    square: number;
}

export interface ApartmentListProps {
    apartments: Array<Apartment>;
}

const columns = [
    {
        title: 'Тип',
        dataIndex: 'apartmentType',
        key: 'apartmentType',
        render: (type: ApartmentType) => type.name,
    },
    {
        title: 'Номер',
        dataIndex: 'number',
        key: 'number',
    },
    {
        title: 'Этаж',
        dataIndex: 'floor',
        key: 'floor',
    },
    {
        title: 'Площадь',
        dataIndex: 'square',
        key: 'square',
        render: (value: number) => <>{value} м<sup>2</sup></>
    },
];

export const ApartmentList = (props: ApartmentListProps) => {
    return <Table columns={columns} dataSource={props.apartments} />;
}