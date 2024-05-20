package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.CommentDao;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Comment;

import java.util.List;
import java.util.Optional;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final CommentDao jpaCommentDao;

    private final BookDao jpaBookDao;


    @Autowired
    public CommentServiceImpl(CommentDao jpaCommentDao, BookDao jpaBookDao) {
        this.jpaCommentDao = jpaCommentDao;
        this.jpaBookDao = jpaBookDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByBookId(long bookId) {
        return jpaCommentDao.findByBookBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findCommentById(long commentId) {
        return jpaCommentDao.findById(commentId);
    }

    @Override
    @Transactional
    public Comment insertComment(String text, long bookId) {
        return save(0, text, bookId);
    }

    @Override
    @Transactional
    public Comment updateComment(long id, String text) {
        return save(id, text, 0);
    }


    @Override
    @Transactional
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
}
