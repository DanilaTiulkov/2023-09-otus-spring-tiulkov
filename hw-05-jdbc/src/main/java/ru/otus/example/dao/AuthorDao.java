package ru.otus.example.dao;

import ru.otus.example.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAllAuthors();

    Optional<Author> findById(long id);
}
