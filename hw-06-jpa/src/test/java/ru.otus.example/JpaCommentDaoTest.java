package ru.otus.example;


import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.CommentDao;
import ru.otus.example.dao.JpaBookDao;
import ru.otus.example.dao.JpaCommentDao;
import ru.otus.example.models.Book;
import ru.otus.example.models.Comment;

import java.util.List;


@DataJpaTest
@Import(JpaCommentDao.class)
public class JpaCommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Поиск комментария по id")
    public void findCommentById() {
        var expectingComment = em.find(Comment.class, 1);
        var actualComment = commentDao.findCommentById(1).orElse(null);
        Assertions.assertEquals(expectingComment, actualComment);
    }

    @Test
    @DisplayName("Поиск комментариев по id книги")
    public void findCommentsByBookId() {
        var expectingComment = em.find(Comment.class, 1);
        List<Comment> actualComments = commentDao.findCommentsByBookId(1);
        assertThat(actualComments).containsExactlyInAnyOrder(expectingComment);
    }

    @Test
    @DisplayName("Удаление комментария")
    public void deleteComment() {
        var deletedComment = em.find(Comment.class, 1);
        commentDao.deleteComment(1);
        List<Comment> actualComments = commentDao.findCommentsByBookId(1);
        assertThat(actualComments).doesNotContain(deletedComment);
        Assertions.assertEquals(0, actualComments.size());
    }

    @Test
    @DisplayName("Обновление комментария")
    public void updateComment() {
        var book = em.find(Book.class, 1);
        var expectingComment = new Comment(3, "Updated comment", book);
        var actualComment = commentDao.save(new Comment(3, "Updated comment", book));
        Assertions.assertEquals(expectingComment, actualComment);
    }

    @Test
    @DisplayName("Создание комментария")
    public void createComment() {
        var book = em.find(Book.class, 1);
        var expectingComment = new Comment(4, "Test comment", book);
        var actualComment = commentDao.save(new Comment(0, "Test comment", book));
        Assertions.assertEquals(expectingComment, actualComment);


    }
}
