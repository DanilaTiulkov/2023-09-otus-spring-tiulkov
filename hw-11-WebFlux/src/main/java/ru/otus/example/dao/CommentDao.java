package ru.otus.example.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.example.models.Comment;


public interface CommentDao extends ReactiveCrudRepository<Comment, Long> {
}
