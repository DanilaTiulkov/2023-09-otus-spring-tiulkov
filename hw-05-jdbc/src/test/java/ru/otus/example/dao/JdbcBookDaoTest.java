package ru.otus.example.dao;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;
import java.util.List;
import java.util.stream.IntStream;

@JdbcTest
@Import(JdbcBookDao.class)
public class JdbcBookDaoTest {

    @Autowired
    private JdbcBookDao bookDao;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    private String[] authorsName;

    private String[] genresTitle;

    private String[] booksTitle;


    @BeforeEach
    void setUp() {
        authorsName = getAuthorsName();
        genresTitle = getGenresTitle();
        booksTitle = getBooksTitle();
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @Test
    @DisplayName("Поиск книги по id")
    public void findById() {
        var expectedBook = dbBooks.get(0);
        var returnedBook = bookDao.findById(1L);
        assertThat(returnedBook).isPresent()
                .get()
                .isEqualTo(expectedBook);

    }

    @Test
    @DisplayName("Поиск всех книг")
    public void findAll() {
        List<Book> returnedBooks = bookDao.findAll();
        List<Book> expectedBooks = dbBooks;
        assertThat(returnedBooks).containsAnyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() {
        assertThat(bookDao.findById(1L)).isPresent();
        bookDao.deleteById(1L);
        assertThat(bookDao.findById(1L)).isEmpty();

    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() {
        var expectedBook = new Book(1L, "Writer", dbAuthors.get(0), dbGenres.get(0));

        assertThat(bookDao.findById(expectedBook.getBookId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var updatedBook = bookDao.save(expectedBook);
        assertThat(updatedBook).isNotNull()
                .matches(book -> book.getBookId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookDao.findById(updatedBook.getBookId()))
                .isPresent()
                .get()
                .isEqualTo(updatedBook);

    }

    @Test
    @DisplayName("Создание книги")
    public void createBook() {
        var expectedBook = new Book(0, "Book Four", dbAuthors.get(1), dbGenres.get(0));
        var savedBook = bookDao.save(expectedBook);

        assertThat(savedBook).isNotNull()
                .matches(book -> book.getBookId() >0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookDao.findById(savedBook.getBookId()))
                .isPresent()
                .get()
                .isEqualTo(savedBook);

    }

    private List<Author> getDbAuthors() {
        return IntStream.range(1,4).boxed()
                .map(id -> new Author(id, authorsName[id-1]))
                .toList();
    }

    private List<Genre> getDbGenres() {
        return IntStream.range(1,4).boxed()
                .map(id -> new Genre(id, genresTitle[id-1]))
                .toList();
    }

    private List<Book> getDbBooks(List<Author> authors, List<Genre> genres) {
        return IntStream.range(1,4).boxed()
                .map(id -> new Book(id, booksTitle[id-1], authors.get(id-1), genres.get(id-1)))
                .toList();
    }

    private String[] getAuthorsName() {
        return new String[]{"Ivan Sergeevich", "Ilya Abramov", "Mikhail Andreevich"};
    }

    private String[] getGenresTitle() {
        return new String[]{"Fantastic", "Adventure", "Horror"};
    }

    private String[] getBooksTitle() {
        return new String[]{"Three planets", "In search of the lost", "Behind a closed door"};
    }
}
