insert into Authors(full_name)
values ('Ivan Sergeevich'), ('Ilya Abramov'), ('Mikhail Andreevich');

insert into Genres(genre_name)
values ('Fantastic'), ('Adventure'), ('Horror');

insert into Books(title, author_id, genre_id)
values ('Three planets', 1, 1), ('In search of the lost', 2, 2), ('Behind a closed door', 3, 3);