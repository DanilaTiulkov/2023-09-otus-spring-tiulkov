package ru.otus.example.services;

import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findCommentsByBookId(String bookId);

    Optional<Comment> findCommentById(String commentId);

    Comment insertComment(String text, String bookId);

    Comment updateComment(String id, String text);

    void deleteComment(String id);
}
