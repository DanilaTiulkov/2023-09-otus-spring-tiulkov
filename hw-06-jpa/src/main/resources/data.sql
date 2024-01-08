insert into Authors(full_name) -- Что будет если наполнять БД, которая не стирает данные после отключения приложения
values ('Ivan Sergeevich'), ('Ilya Abramov'), ('Mikhail Andreevich');

insert into Genres(genre_name)
values ('Fantastic'), ('Adventure'), ('Horror');

insert into Books(title, author_id, genre_id)
values ('Three planets', 1, 1), ('In search of the lost', 2, 2), ('Behind a closed door', 3, 3), ('Marsianse', 1, 1);;

insert into Comments(comment_text, book_id)
values ('Не понравилось', 1), ('Понравилось', 2), ('Я очень испугался!', 3);