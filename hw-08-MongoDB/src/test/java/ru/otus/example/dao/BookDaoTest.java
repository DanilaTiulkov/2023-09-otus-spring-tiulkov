package ru.otus.example.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;

import java.util.List;

@DataMongoTest
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MongoOperations mo;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;


    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks();
    }

    @Test
    @DisplayName("Поиск книги по id")
    public void findById() {
        var expectedBook = dbBooks.get(0);
        var returnedBook = bookDao.findById(dbBooks.get(0).getBookId());
        assertThat(returnedBook)
                .isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("Поиск всех книг")
    public void findAll() {
        List<Book> returnedBooks = bookDao.findAll();
        List<Book> expectedBooks = dbBooks;
        assertThat(returnedBooks).containsExactlyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() {
        String bookId = dbBooks.get(0).getBookId();
        assertThat(bookId).isNotNull();
        bookDao.deleteById(bookId);
        assertThat(bookDao.findById(bookId)).isEmpty();

    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() {
        String bookId = dbBooks.get(0).getBookId();
        var expectedBook = new Book(bookId, "Writer", dbAuthors.get(0), dbGenres.get(0));

        assertThat(dbBooks.get(0))
                .isNotNull()
                .isNotEqualTo(expectedBook);

        var updatedBook = bookDao.save(expectedBook);
        assertThat(updatedBook).isNotNull()
                .matches(book -> book.getBookId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookDao.findById(bookId))
                .isPresent()
                .get()
                .isEqualTo(updatedBook);

    }

    @Test
    @DisplayName("Создание книги")
    public void createBook() {
        var expectedBook = new Book(null, "Book Four", dbAuthors.get(1), dbGenres.get(0));
        var savedBook = bookDao.save(expectedBook);

        assertThat(savedBook).isNotNull()
                .matches(book -> book.getBookId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookDao.findById(savedBook.getBookId()))
                .isPresent()
                .get()
                .isEqualTo(savedBook);

    }

    private List<Author> getDbAuthors() {
        return mo.findAll(Author.class);
    }

    private List<Genre> getDbGenres() {
        return mo.findAll(Genre.class);
    }

    private List<Book> getDbBooks() {
        return mo.findAll(Book.class);
    }
}

