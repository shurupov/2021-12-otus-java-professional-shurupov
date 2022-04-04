create table address
(
    id   bigint not null primary key,
    street varchar(50)
);

create table phone
(
    id   bigint not null primary key,
    client_id bigint not null references client(id),
    number varchar(50)
);

alter table client
    add address_id bigint not null references address(id);
