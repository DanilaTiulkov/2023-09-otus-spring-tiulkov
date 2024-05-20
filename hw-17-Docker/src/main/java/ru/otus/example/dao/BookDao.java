package ru.otus.example.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.example.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(value = "book-entity-graph")
    List<Book> findAll();

    @Override
    @EntityGraph(value = "book-entity-graph")
    Optional<Book> findById(Long bookId);
}
