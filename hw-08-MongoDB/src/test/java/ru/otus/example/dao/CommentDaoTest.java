package ru.otus.example.dao;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.example.models.Book;
import ru.otus.example.models.Comment;

import java.util.List;


@DataMongoTest
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BookDao bookDao;

    private List<Comment> dbComments;

    private List<Book> dbBooks;


    @BeforeEach
    public void init() {
        dbComments = getComments();
        dbBooks = getBooks();
    }

    @Test
    @DisplayName("Поиск комментария по id")
    public void findCommentById() {
        String commentId = dbComments.get(0).getCommentId();
        var expectingComment = dbComments.get(0);
        var actualComment = commentDao.findById(commentId);
        assertThat(actualComment)
                .isPresent()
                .get()
                .isEqualTo(expectingComment);
    }

    @Test
    @DisplayName("Поиск комментариев по id книги")
    public void findCommentsByBookId() {
        String bookId = dbBooks.get(0).getBookId();
        List<Comment> expectingComments = List.of(dbComments.get(0));
        List<Comment> actualComments = commentDao.findByBookBookId(bookId);
        assertThat(actualComments).containsExactlyElementsOf(expectingComments);
    }

    @Test
    @DisplayName("Удаление комментария")
    public void deleteComment() {
        var deletedComment = dbComments.get(0);
        assertThat(deletedComment).isNotNull();

        commentDao.deleteById(deletedComment.getCommentId());
        var actualComment = commentDao.findById(deletedComment.getCommentId());
        assertThat(actualComment).isEmpty();
    }

    @Test
    @DisplayName("Обновление комментария")
    public void updateComment() {
        String commentId = dbComments.get(2).getCommentId();
        var book = dbBooks.get(2);
        var expectingComment = new Comment(commentId, "Updated comment", book);

        assertThat(commentDao.findById(expectingComment.getCommentId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectingComment);
        var updatedComment = commentDao.save(expectingComment);

        assertThat(updatedComment).isNotNull()
                .matches(comment -> comment.getCommentId() != null )
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(expectingComment);

        assertThat(commentDao.findById(updatedComment.getCommentId()))
                .isPresent()
                .get()
                .isEqualTo(expectingComment);

    }

    @Test
    @DisplayName("Создание комментария")
    public void createComment() {
        var book = dbBooks.get(1);
        var expectingComment = new Comment(null, "Test comment", book);
        var savedComment = commentDao.save(expectingComment);

        assertThat(savedComment)
                .isNotNull()
                .matches(comment -> comment.getCommentId() != null )
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(expectingComment);

        assertThat(commentDao.findById(savedComment.getCommentId()))
                .isPresent()
                .get()
                .isEqualTo(savedComment);


    }

    public List<Comment> getComments() {
        return commentDao.findAll();
    }

    public List<Book> getBooks() {
        return bookDao.findAll();
    }
}
