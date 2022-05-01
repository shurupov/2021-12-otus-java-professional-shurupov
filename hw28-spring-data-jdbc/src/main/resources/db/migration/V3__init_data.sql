insert into address (street)
    values
    ('Aerodromnaya street'),
    ('Lenina prospect'),
    ('Novo-Sadovaya street');

insert into client (id, name, address_id)
    values
    (1, 'Pushkin', 1),
    (2, 'Ivanov', 2),
    (3, 'Petrov', 3);

insert into phone (client_id, number)
    values
    (1, '12345'),
    (1, '23456'),
    (2, '345678'),
    (2, '456789'),
    (2, '567890'),
    (3, '678901');

alter sequence hibernate_sequence restart with 10;