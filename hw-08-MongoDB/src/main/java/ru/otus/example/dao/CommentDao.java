package ru.otus.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.example.models.Comment;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment, String> {

    List<Comment> findByBookBookId(String bookId);

    void deleteByBookBookId(String bookId);
}
