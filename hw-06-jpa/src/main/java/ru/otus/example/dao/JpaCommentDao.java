package ru.otus.example.dao;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;


@Repository("commentDao")
public class JpaCommentDao implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    public JpaCommentDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where book.bookId = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findCommentById(long commentId) {
        Comment comment;
        try {
            comment = em.find(Comment.class, commentId);
        } catch (NoResultException e) {
            comment = null;
        }
        return Optional.ofNullable(comment);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getCommentId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteComment(long id) {
        em.remove(em.find(Comment.class, id));
    }
}
