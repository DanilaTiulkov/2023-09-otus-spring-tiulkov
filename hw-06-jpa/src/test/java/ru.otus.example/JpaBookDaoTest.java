package ru.otus.example;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.example.dao.JpaAuthorDao;
import ru.otus.example.dao.JpaBookDao;
import ru.otus.example.dao.JpaGenreDao;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import({JpaBookDao.class, JpaAuthorDao.class, JpaGenreDao.class})
public class JpaBookDaoTest {

    @Autowired
    private JpaBookDao bookDao;
    @Autowired
    private TestEntityManager em;
    @MockBean
    private JpaGenreDao jpaGenreDao;
    @MockBean
    private JpaAuthorDao jpaAuthorDao;



    @Test
    @DisplayName("Поиск книги по id")
    public void findById() {
        var expectingBook = em.find(Book.class, 1);
        var actualBook = bookDao.findById(1).orElse(null);
        Assertions.assertEquals(expectingBook, actualBook);
    }

    @Test
    @DisplayName("Поиск всех книг")
    public void findAll() {
        var firstBook = em.find(Book.class, 1);
        var secondBook = em.find(Book.class, 2);
        var thirdBook = em.find(Book.class, 3);
        var fourthBook = em.find(Book.class, 4);
        List<Book> actualBooks = bookDao.findAll();
        assertThat(actualBooks).containsExactlyInAnyOrder(firstBook, secondBook, thirdBook, fourthBook);
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() {
        var deletedBook = em.find(Book.class, 1);
        bookDao.deleteById(1);
        List<Book> books = bookDao.findAll();
        assertThat(books).doesNotContain(deletedBook);
        Assertions.assertEquals(3, books.size());
    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() {
//        var author = new Author(1, "Ivan Sergeevich");
//        var genre = new Genre(1, "Fantastic");
        var author = em.find(Author.class, 1);
        var genre = em.find(Genre.class, 1);
        var expectingBook = new Book(1, "Writer", author, genre);

        var actualBook = bookDao.save(new Book(1, "Writer", author, genre));
//        Assertions.assertEquals(expectingBook, actualBook);
        Assertions.assertEquals(actualBook, expectingBook);
    }

    @Test
    @DisplayName("Создание книги")
    public void createBook() {
        var author = new Author(2, "Ilya Abramov");
        var genre = new Genre(1, "Fantastic");
        var authorOptional = Optional.of(author);
        var genreOptional = Optional.of(genre);
        var expectingBook = new Book(5, "Book four", author, genre);

        Mockito.when(jpaAuthorDao.findById(2)).thenReturn(authorOptional);
        Mockito.when(jpaGenreDao.findById(1)).thenReturn(genreOptional);

        var book = bookDao.save(new Book(0, "Book four", author, genre));
        Assertions.assertEquals(expectingBook, book);
    }
}
