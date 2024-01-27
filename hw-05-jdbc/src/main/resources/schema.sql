create table Authors(
    author_id bigint auto_increment PRIMARY KEY,
    full_name varchar(255)
);
create table Genres(
    genre_id bigint auto_increment PRIMARY KEY,
    genre_name varchar(255)
);
create table Books(
    book_id bigint auto_increment PRIMARY KEY,
    title varchar(255),
    author_id bigint,
    genre_id bigint,
    foreign key (author_id) references Authors(author_id) on delete cascade,
    foreign key (genre_id) references Genres(genre_id) on delete cascade
);