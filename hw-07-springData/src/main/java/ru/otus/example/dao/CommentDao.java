package ru.otus.example.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {

    @EntityGraph(value = "comment-entity-graph")
    List<Comment> findByBookBookId(long bookId);
}
