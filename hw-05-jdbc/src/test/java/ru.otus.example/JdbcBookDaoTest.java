package ru.otus.example;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.example.dao.JdbcAuthorDao;
import ru.otus.example.dao.JdbcBookDao;
import ru.otus.example.dao.JdbcGenreDao;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;
import ru.otus.example.services.BookServiceImpl;
import java.util.List;
import java.util.Optional;

@JdbcTest
@Import({JdbcBookDao.class, BookServiceImpl.class, JdbcAuthorDao.class, JdbcGenreDao.class})
public class JdbcBookDaoTest {

    @Autowired
    private JdbcBookDao bookDao;
    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private JdbcGenreDao jdbcGenreDao;
    @MockBean
    private JdbcAuthorDao jdbcAuthorDao;



    @Test
    @DisplayName("Поиск книги по id")
    public void findById() {
        Author author = new Author(1, "Иван Сергеевич");
        Genre genre = new Genre(1, "Фантастика");
        Book expectingBook = new Book(1, "Три планеты", author, genre);
        Book book = bookService.findById(1).orElse(null);
        Assertions.assertEquals(expectingBook, book);
    }

    @Test
    @DisplayName("Поиск всех книг")
    public void findAll() { //Если есть база с большим количеством записей, как протестировать этот метод, не в ручную же добавлять каждый объект?
        Author firstAuthor = new Author(1, "Иван Сергеевич");
        Genre firstGenre = new Genre(1, "Фантастика");
        Book firstBook = new Book(1, "Три планеты", firstAuthor, firstGenre);
        Author secondAuthor = new Author(2, "Илья Абрамов");
        Genre secondGenre = new Genre(2, "Приключение");
        Book secondBook = new Book(2, "В поисках потерянного", secondAuthor, secondGenre);
        Author thirdAuthor = new Author(3, "Михаил Андреевич");
        Genre thirdGenre = new Genre(3, "Ужасы");
        Book thirdBook = new Book(3, "За закрытой дверью", thirdAuthor, thirdGenre);
        List<Book> actualBooks = bookService.findAll();
        assertThat(actualBooks).containsExactlyInAnyOrder(firstBook, secondBook, thirdBook);
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() {
        Author author = new Author(1, "Иван Сергеевич");
        Genre genre = new Genre(1, "Фантастика");
        Book deletedBook = new Book(1, "Три планеты", author, genre);
        bookService.delete(1);
        List<Book> books = bookService.findAll();
        assertThat(books).doesNotContain(deletedBook);
        Assertions.assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() {
        Author author = new Author(1, "Иван Сергеевич");
        Genre genre = new Genre(1, "Фантастика");
        Optional<Author> authorOptional = Optional.of(author);
        Optional<Genre> genreOptional = Optional.of(genre);
        Book expectingBook = new Book(1, "Писатель", author, genre);

        Mockito.when(jdbcAuthorDao.findById(1)).thenReturn(authorOptional);
        Mockito.when(jdbcGenreDao.findById(1)).thenReturn(genreOptional);

        Book book = bookService.update(1, "Писатель", 1, 1);
        Assertions.assertEquals(expectingBook, book);
    }

    @Test
    @DisplayName("Создание книги")
    public void createBook() {
        Author author = new Author(2, "Илья Абрамов");
        Genre genre = new Genre(1, "Фантастика");
        Optional<Author> authorOptional = Optional.of(author);
        Optional<Genre> genreOptional = Optional.of(genre);
        Book expectingBook = new Book(4, "Четвертая книга", author, genre);

        Mockito.when(jdbcAuthorDao.findById(2)).thenReturn(authorOptional);
        Mockito.when(jdbcGenreDao.findById(1)).thenReturn(genreOptional);

        Book book = bookService.insert("Четвертая книга", 2, 1);
        Assertions.assertEquals(expectingBook, book);
    }
}
