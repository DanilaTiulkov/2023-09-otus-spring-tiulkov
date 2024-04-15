package ru.otus.example.mapper;

import io.r2dbc.spi.Readable;
import org.springframework.stereotype.Component;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;

@Component
public class BookMapper {

    public Book toModel(Readable readable) {
        var bookId = readable.get("book_id", Long.class);
        var title = readable.get("title", String.class);
        var author = new Author(readable.get("author_id", Long.class), readable.get("full_name", String.class));
        var genre = new Genre(readable.get("genre_id", Long.class), readable.get("genre_name", String.class));
        return new Book(bookId, title, author, genre);
    }
}
