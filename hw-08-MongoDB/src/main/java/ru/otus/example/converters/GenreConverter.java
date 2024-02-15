package ru.otus.example.converters;

import org.springframework.stereotype.Component;
import ru.otus.example.models.Genre;

@Component
public class GenreConverter {

    public String genreToString(Genre genre) {
        return "Id: %s. Genre: %s.".formatted(genre.getGenreId(), genre.getGenreName());
    }
}
