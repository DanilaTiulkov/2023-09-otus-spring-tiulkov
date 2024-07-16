insert into Brands(name, country) values ('Bosh', 'Германия'),
                                         ('Makita', 'Япония'),
                                         ('Bork', 'Китай');

insert into Categories(name) values ('Дрели'),
                                    ('Пилы'),
                                    ('Компрессоры');

insert into Products(title, price, quantity, category_id) values ('Bosch GSR 180-Li Professional', 18000, 2, 1),
                                                       ('Дисковая пила Makita HS301DZ', 7790, 5, 2),
                                                       ('Воздуходувка портативная беспроводная аккумуляторная', 2184, 6, 3);

insert into Characteristics(battery_capacity, color, size, brand_id, product_id) values (2, 'Голубой', '198x62x225 ', 1, 1),
                                                                            (4, 'Голубой', '190x330x180', 2, 2),
                                                                            (6, 'Черный', '6.5x10x17', 3, 3);

insert into Users(username, password, role) values
                                                ('user', '$2a$12$NEY17oift3BTOrn6BVPWjOp0B7lHlfEtTp0vQuRqNw98Hj50kXQ4S', 'USER'),
                                                --login = user, password = password
                                                ('admin', '$2a$12$MIZaO9ik.n7jqSezzhMuIet.fypWTjk3/ZZbEerIMxHAlQAHg6R2i', 'ADMIN')
                                                --login = admin, password = admin

