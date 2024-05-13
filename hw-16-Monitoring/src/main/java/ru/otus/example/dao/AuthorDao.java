package ru.otus.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.example.models.Author;


public interface AuthorDao extends JpaRepository<Author, Long> {
}
