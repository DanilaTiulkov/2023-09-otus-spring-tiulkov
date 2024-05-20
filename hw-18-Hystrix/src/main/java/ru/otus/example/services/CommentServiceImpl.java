package ru.otus.example.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.CommentDao;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("commentService")
@Slf4j
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentDao jpaCommentDao;

    private final BookDao jpaBookDao;


    @Autowired
    public CommentServiceImpl(CommentDao jpaCommentDao, BookDao jpaBookDao) {
        this.jpaCommentDao = jpaCommentDao;
        this.jpaBookDao = jpaBookDao;
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "findCommentsBreaker", fallbackMethod = "findCommentsFallBack")
    public List<Comment> findCommentsByBookId(long bookId) {
        return jpaCommentDao.findByBookBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "findCommentBreaker", fallbackMethod = "findCommentFallBack")
    public Optional<Comment> findCommentById(long commentId) {
        return jpaCommentDao.findById(commentId);
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "insertCommentBreaker", fallbackMethod = "insertCommentFallBack")
    public Comment insertComment(String text, long bookId) {
        return save(0, text, bookId);
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "updateCommentBreaker", fallbackMethod = "updateCommentFallBack")
    public Comment updateComment(long id, String text) {
        return save(id, text, 0);
    }


    @Override
    @Transactional
    @CircuitBreaker(name = "deleteCommentBreaker", fallbackMethod = "deleteCommentFallBack")
    public void deleteComment(long id) {
        jpaCommentDao.deleteById(id);
    }

    public Comment save(long id, String text, long bookId) {
        Comment comment;
        if (bookId != 0) {
            var book = jpaBookDao.findById(bookId).orElseThrow(() ->
                    new EntityNotFoundException("Book not found"));
            comment = new Comment(id, text, book);
        } else {
            comment = jpaCommentDao.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Comment not found"));
            comment.setcommentText(text);
        }
        return jpaCommentDao.save(comment);
    }

    private List<Comment> findBooksFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new ArrayList<>();
    }

    private Optional<Comment> findBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        var book = new Comment(0, null, null);
        return Optional.of(book);
    }

    private Comment insertBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new Comment(0, null, null);
    }

    private Comment updateBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new Comment(0, null, null);
    }

    private void deleteBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
    }
}
