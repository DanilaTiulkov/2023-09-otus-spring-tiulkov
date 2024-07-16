package ru.otus.example.services;

import ru.otus.example.models.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();
}
