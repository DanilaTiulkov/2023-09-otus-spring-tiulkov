package ru.otus.example.services;

import ru.otus.example.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAllAuthors();
}
