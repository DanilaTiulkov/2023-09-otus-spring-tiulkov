package ru.otus.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.otus.example.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends JpaRepository<Author, Long> {
}
