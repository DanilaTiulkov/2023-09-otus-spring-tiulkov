package ru.otus.example.dao;

import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> findById(long id);
    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);
}
