package ru.otus.example.dao;

import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    List<Comment> findCommentsByBookId(long bookId);

    Optional<Comment> findCommentById(long commentId);

    Comment save(Comment comment);

    void deleteComment(long id);
}
