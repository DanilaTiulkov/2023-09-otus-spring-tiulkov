package ru.otus.example.dao;


import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.example.models.Book;
import ru.otus.example.models.Comment;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@Import(JpaCommentDao.class)
public class JpaCommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    private List<Comment> dbCommentsByBookId;


    @BeforeEach
    public void init() {
        dbCommentsByBookId = commentsByBookId();
    }

    @Test
    @DisplayName("Поиск комментария по id")
    public void findCommentById() {
        var expectingComment = em.find(Comment.class, 1);
        var actualComment = em.find(Comment.class, 1);
        assertThat(actualComment)
                .isNotNull()
                .isEqualTo(expectingComment);
    }

    @Test
    @DisplayName("Поиск комментариев по id книги")
    public void findCommentsByBookId() {
        List<Comment> expectingComments = dbCommentsByBookId;
        List<Comment> actualComments = commentDao.findCommentsByBookId(1);
        assertThat(actualComments).containsAnyElementsOf(expectingComments);
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

        assertThat(em.find(Comment.class, expectingComment.getCommentId()))
                .isNotNull()
                .isNotEqualTo(expectingComment);
        var updatedComment = commentDao.save(expectingComment);

        assertThat(updatedComment).isNotNull()
                .matches(comment -> comment.getCommentId() > 0 )
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(expectingComment);

        assertThat(em.find(Comment.class, updatedComment.getCommentId()))
                .isNotNull()
                .isEqualTo(expectingComment);

    }

    @Test
    @DisplayName("Создание комментария")
    public void createComment() {
        var book = em.find(Book.class, 1);
        var expectingComment = new Comment(0, "Test comment", book);
        var savedComment = commentDao.save(expectingComment);

        assertThat(savedComment)
                .isNotNull()
                .matches(comment -> comment.getCommentId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(expectingComment);

        assertThat(em.find(Comment.class, savedComment.getCommentId()))
                .isNotNull()
                .isEqualTo(savedComment);


    }

    public List<Comment> commentsByBookId() {
        List<Comment> comments = new ArrayList<>();
        var comment = em.find(Comment.class, 1);
        comments.add(comment);
        return comments;
    }
}
