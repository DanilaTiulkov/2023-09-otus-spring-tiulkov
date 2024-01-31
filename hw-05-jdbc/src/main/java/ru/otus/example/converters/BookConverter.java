package ru.otus.example.converters;

import org.springframework.stereotype.Component;
import ru.otus.example.models.Book;

@Component
public class BookConverter {

    public String bookToString(Book book) {
        return "Id: %d.\n Title: %s.\n Author: %s.\n Genre: %s".
                formatted(book.getBookId(),
                        book.getTitle(),
                        book.getAuthor().getFullName(),
                        book.getGenre().getGenreName());
    }
}
