package ru.otus.example;


import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.example.dao.CommentDao;
import ru.otus.example.models.Book;
import ru.otus.example.models.Comment;

import java.util.List;


@DataJpaTest
public class JpaCommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("Поиск комментариев по id книги")
    public void findCommentsByBookId() {
        var expectingComment = em.find(Comment.class, 1);
        List<Comment> actualComments = commentDao.findByBookBookId(1);
        assertThat(actualComments).containsExactlyInAnyOrder(expectingComment);
    }
}
