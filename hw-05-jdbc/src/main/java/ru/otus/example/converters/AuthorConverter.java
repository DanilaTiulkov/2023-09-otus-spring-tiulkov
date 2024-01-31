package ru.otus.example.converters;

import org.springframework.stereotype.Component;
import ru.otus.example.models.Author;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        return "Id: %d. Author name: %s\n".formatted(author.getAuthorId(), author.getFullName());
    }
}
