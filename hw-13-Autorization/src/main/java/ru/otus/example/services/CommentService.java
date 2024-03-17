package ru.otus.example.services;

import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findCommentsByBookId(long bookId);

    Optional<Comment> findCommentById(long commentId);

    Comment insertComment(String text, long bookId);

    Comment updateComment(long id, String text);

    void deleteComment(long id);
}
