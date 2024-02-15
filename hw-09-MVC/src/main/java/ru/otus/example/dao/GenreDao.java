package ru.otus.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.example.models.Genre;


public interface GenreDao extends JpaRepository<Genre, Long> {
}
