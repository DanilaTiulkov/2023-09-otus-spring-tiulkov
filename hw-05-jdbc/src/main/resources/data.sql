insert into Authors(full_name)
values ('Иван Сергеевич'), ('Илья Абрамов'), ('Михаил Андреевич');

insert into Genres(genre_name)
values ('Фантастика'), ('Приключение'), ('Ужасы');

insert into Books(title, author_id, genre_id)
values ('Три планеты', 1, 1), ('В поисках потерянного', 2, 2), ('За закрытой дверью', 3, 3);