package ru.otus.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.GenreDto;
import ru.otus.example.model.mongo.GenreDoc;

import java.util.Map;

@Service
public class GenreServiceImpl implements GenreService {

    private final Map<String, Long> genresIds;

    @Autowired
    public GenreServiceImpl(Map<String, Long> genresIds) {
        this.genresIds = genresIds;
    }

    @Override
    public GenreDto transform(GenreDoc genreDoc) {
        return new GenreDto(0, genreDoc.getGenreId(), genreDoc.getGenreName());
    }

    @Override
    public Map<String, Long> findAllGenresIds() {
        return genresIds;
    }
}
