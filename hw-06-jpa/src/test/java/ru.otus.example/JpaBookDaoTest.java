package ru.otus.example;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.JpaAuthorDao;
import ru.otus.example.dao.JpaBookDao;
import ru.otus.example.dao.JpaGenreDao;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import({JpaBookDao.class, JpaAuthorDao.class, JpaGenreDao.class})
public class JpaBookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;

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
                .matches(book -> book.getBookId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookDao.findById(savedBook.getBookId()))
                .isPresent()
                .get()
                .isEqualTo(savedBook);

    }

    private List<Author> getDbAuthors() {
        List<Author> authors = new ArrayList<>();
        var firstAuthor = em.find(Author.class, 1);
        var secondAuthor = em.find(Author.class, 2);
        var thirdAuthor = em.find(Author.class, 3);
        var fourthAuthor = em.find(Author.class, 4);
        authors.add(firstAuthor);
        authors.add(secondAuthor);
        authors.add(thirdAuthor);
        authors.add(fourthAuthor);
        return authors;
    }

    private List<Genre> getDbGenres() {
        List<Genre> genres = new ArrayList<>();
        var firstGenre = em.find(Genre.class, 1);
        var secondGenre = em.find(Genre.class, 2);
        var thirdGenre = em.find(Genre.class, 3);
        var fourthGenre = em.find(Genre.class, 4);
        genres.add(firstGenre);
        genres.add(secondGenre);
        genres.add(thirdGenre);
        genres.add(fourthGenre);
        return genres;
    }

    private List<Book> getDbBooks() {
        List<Book> books = new ArrayList<>();
        var firstBook = em.find(Book.class, 1);
        var secondBook = em.find(Book.class, 2);
        var thirdBook = em.find(Book.class, 3);
        var fourthBook = em.find(Book.class, 4);
        books.add(firstBook);
        books.add(secondBook);
        books.add(thirdBook);
        books.add(fourthBook);
        return books;
    }
}
