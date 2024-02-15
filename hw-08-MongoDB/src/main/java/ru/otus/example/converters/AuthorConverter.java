package ru.otus.example.converters;

import org.springframework.stereotype.Component;
import ru.otus.example.models.Author;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        return "Id: %s. Author name: %s.".formatted(author.getAuthorId(), author.getFullName());
    }
}
