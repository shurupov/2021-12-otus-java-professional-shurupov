create table public.building (
    id serial primary key,
    address varchar(100) not null
);

create table public.apartment_type (
    id serial primary key,
    name varchar(100) not null
);

create table public.apartment (
    id serial primary key,
    building_id int references public.building(id) not null,
    apartment_type_id int references public.apartment_type(id) not null,
    number int not null,
    floor int not null,
    square numeric(4) not null
);

create table public.apartment_user (
    id serial primary key,
    full_name varchar(100) not null,
    short_name varchar(100),
    phone_number varchar(20),
    telegram varchar(100),
    username varchar(50),
    password varchar(150),
    salt varchar(10),
    roles jsonb,
    reg_hash varchar(50)
);

create table public.ownership_type (
    id serial primary key,
    name varchar(100) not null
);

create table public.ownership (
    id serial primary key,
    apartment_id int references public.apartment(id) not null,
    user_id int references public.apartment_user(id) not null,
    ownership_type_id int references public.ownership_type(id) not null,
    share real,
    decision_maker boolean not null default true
);
