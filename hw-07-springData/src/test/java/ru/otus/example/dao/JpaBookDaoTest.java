package ru.otus.example.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
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
        var returnedBook = em.find(Book.class, 1L);
        assertThat(returnedBook)
                .isNotNull()
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
        assertThat(em.find(Book.class, 1L)).isNotNull();
        bookDao.deleteById(1L);
        assertThat(em.find(Book.class, 1L)).isNull();

    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() {
        var expectedBook = new Book(1L, "Writer", dbAuthors.get(0), dbGenres.get(0));

        assertThat(em.find(Book.class, expectedBook.getBookId()))
                .isNotNull()
                .isNotEqualTo(expectedBook);

        var updatedBook = bookDao.save(expectedBook);
        assertThat(updatedBook).isNotNull()
                .matches(book -> book.getBookId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(em.find(Book.class, updatedBook.getBookId()))
                .isNotNull()
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

        assertThat(em.find(Book.class, savedBook.getBookId()))
                .isNotNull()
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

