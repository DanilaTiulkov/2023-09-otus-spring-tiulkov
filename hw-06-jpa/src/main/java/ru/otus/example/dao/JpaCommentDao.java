package ru.otus.example.dao;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;


@Repository("commentDao")
public class JpaCommentDao implements CommentDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-entity-graph");
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where book.bookId = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findCommentById(long commentId) {
        Comment comment;
        try {
            TypedQuery<Comment> query = em.createQuery("select c " +
                    "from Comment c " +
                    "where commentId = :commentId", Comment.class);
            query.setParameter("commentId", commentId);
            comment = query.getSingleResult();
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
        try {
            TypedQuery<Integer> query = em.createQuery("select 1 from Comment c where commentId = :id", Integer.class);
            query.setParameter("id", comment.getCommentId());
            query.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Comment not found");
        }
        return em.merge(comment);
    }

    @Override
    public void deleteComment(long id) {
        em.remove(em.find(Comment.class, id));
    }
}
