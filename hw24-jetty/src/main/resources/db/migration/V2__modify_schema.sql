create table address
(
    id   bigserial primary key,
    street varchar(50)
);

create table phone
(
    id   bigserial primary key,
    client_id bigint references client(id),
    number varchar(50)
);

alter table client
    add address_id bigint references address(id);
