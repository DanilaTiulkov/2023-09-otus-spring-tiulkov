package ru.otus.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.example.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Long> {
}
