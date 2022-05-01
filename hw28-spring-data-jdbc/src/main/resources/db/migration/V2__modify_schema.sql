create table address
(
    id   bigserial primary key,
    client_id bigint references client(id),
    street varchar(50)
);

create table phone
(
    id   bigserial primary key,
    client_id bigint references client(id),
    number varchar(50)
);

