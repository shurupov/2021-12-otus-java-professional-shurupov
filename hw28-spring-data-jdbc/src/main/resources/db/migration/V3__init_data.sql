insert into client (id, name)
values
    (1, 'Pushkin'),
    (2, 'Ivanov'),
    (3, 'Petrov');

insert into address (client_id, street)
    values
    (1, 'Aerodromnaya street'),
    (2, 'Lenina prospect'),
    (3, 'Novo-Sadovaya street');

insert into phone (client_id, number)
    values
    (1, '12345'),
    (1, '23456'),
    (2, '345678'),
    (2, '456789'),
    (2, '567890'),
    (3, '678901');

alter sequence hibernate_sequence restart with 10;