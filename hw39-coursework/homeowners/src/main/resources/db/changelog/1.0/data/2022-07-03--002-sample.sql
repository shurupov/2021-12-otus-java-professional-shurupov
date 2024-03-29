insert into building (address) values ('Проспект Ленина, 3');

insert into apartment_type (id, name) values
    (1, 'Квартира'),
    (2, 'Паркинг'),
    (3, 'Коммерческая площадь');

insert into apartment (building_id, apartment_type_id, number, floor, square) values
    (1, 1, 1, 1, 50),
    (1, 1, 2, 1, 100),
    (1, 1, 3, 1, 70),
    (1, 1, 4, 1, 80),
    (1, 1, 5, 1, 100),
    (1, 1, 6, 1, 50),
    (1, 1, 7, 2, 50),
    (1, 1, 8, 2, 100),
    (1, 1, 9,  2, 70),
    (1, 1, 10, 2, 80),
    (1, 1, 11, 2, 100),
    (1, 1, 12, 2, 50),
    (1, 1, 13, 3, 50),
    (1, 1, 14, 3, 100),
    (1, 1, 15, 3, 70),
    (1, 1, 16, 3, 80),
    (1, 1, 17, 3, 100),
    (1, 1, 18, 3, 50),
    (1, 1, 19, 4, 50),
    (1, 1, 20, 4, 100),
    (1, 1, 21, 4, 70),
    (1, 1, 22, 4, 80),
    (1, 1, 23, 4, 100),
    (1, 1, 24, 4, 50);

insert into apartment_user (full_name, short_name, reg_hash) values
    ('Петров Пётр Петрович', 'Петров Пётр', '1f26e9a9cd3f307f7c6d11c4170dd7c4'),
    ('Иванов Иван Иванович', 'Иванов Иван', '40fe2c5a91557a5f23696538f33715b5'),
    ('Иванова Марфа Евгеньевна', 'Иванова Марфа', '8eccd1c2e779135122a744f0c7ca27ab'),
    ('Сергеев Сергей Сергеевич', 'Сергеев Сергей', '0f1dc9483fae31f79d08d7160cbf4511'),
    ('Сергеева Анна Николаевна', 'Сергеева Анна', '21771cf6c9427cc50277d47b2e86dd51'),
    ('Антонов Антон Антонович', 'Антонов Антон', '319f7f60edaa74e0993857bc3ff08b19'),
    ('Антонова Юлия Валерьевна', 'Антонова Юлия', '54dd61d657188ac043d117eb97fa4eae');

insert into ownership_type (id, name) values
    (1, 'Единоличная собственность'),
    (2, 'Совместная собственность'),
    (3, 'Долевая собственность'),
    (4, 'Не собственник');

insert into ownership (apartment_id, user_id, ownership_type_id, share) values
    (1,  1, 1, 1),
    (3,  1, 1, 1),
    (5,  2, 2, 1),
    (5,  4, 3, 0.7),
    (10, 5, 3, 0.3),
    (11, 6, 4, 0),
    (11, 7, 4, 0);

