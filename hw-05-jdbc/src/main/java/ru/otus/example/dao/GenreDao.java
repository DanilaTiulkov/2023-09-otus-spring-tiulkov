package ru.otus.example.dao;

import ru.otus.example.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    List<Genre> findAll();
    Optional<Genre> findById(long id);
}
