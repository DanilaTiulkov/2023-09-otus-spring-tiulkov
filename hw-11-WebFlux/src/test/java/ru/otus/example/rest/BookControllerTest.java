package ru.otus.example.rest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.BookDaoCustomImpl;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;
import ru.otus.example.models.dto.BookCreateDto;
import ru.otus.example.models.dto.BookUpdateDto;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@WebFluxTest(BookController.class)
public class BookControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookDaoCustomImpl bookDaoCustom;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    private Flux<Book> dbBooks;

    private String[] authorsName;

    private String[] genresTitle;

    private String[] booksTitle;


    @BeforeEach
    public void init() {
        authorsName = getAuthorsName();
        genresTitle = getGenresTitle();
        booksTitle = getBooksTitle();
        dbBooks = getDbBooks();
    }

    @Test
    @DisplayName("Поиск книги по id")
    public void findByid() throws Exception {
        var expectedBook = new Book(1, "Three planets",
                   new Author(1, "Ivan Sergeevich"),
                   new Genre(1, "Fantastic"));

        when(bookDaoCustom.findById(1L)).thenReturn(Mono.just(expectedBook));

        webTestClient.get()
                .uri("/api/books/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Book.class)
                .consumeWith(result -> {
                    var actualBook = result.getResponseBody();
                    assertThat(actualBook).isNotNull().isEqualTo(expectedBook);
                });
    }

    @Test
    @DisplayName("Поиск всех книг")
    public void findBooks() {
        var expectedBooks = getBookList(getAuthorList(), getGenreList()).toArray(new Book[0]);
        var expectedSize = 3;

        when(bookDaoCustom.findAll()).thenReturn(dbBooks);

        webTestClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Book.class)
                .hasSize(expectedSize)
                .contains(expectedBooks);
    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() throws Exception {
        var bookUpdateDto = new BookUpdateDto(1, "Two planets", 1L, 1L);
        var genre = new Genre(1L , "Fantastic");
        var author = new Author(1L, "Ivan Sergeevich");
        var book = new Book(1, "Two planets", author, genre);

        when(authorDao.findById(1L)).thenReturn(Mono.just(author));
        when(genreDao.findById(1L)).thenReturn(Mono.just(genre));
        when(bookDaoCustom.save(book)).thenReturn(Mono.just(book));

        webTestClient.put()
                .uri("/api/books/1")
                .bodyValue(bookUpdateDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("Сохранение книги")
    public void saveBook() throws Exception {
        var bookCreateDto = new BookCreateDto("Test book", 1L, 1L);
        var genre = new Genre(1L , "Fantastic");
        var author = new Author(1L, "Ivan Sergeevich");
        var book = new Book(0, "Test book", author, genre);

        when(authorDao.findById(1L)).thenReturn(Mono.just(author));
        when(genreDao.findById(1L)).thenReturn(Mono.just(genre));
        when(bookDaoCustom.save(book)).thenReturn(Mono.just(book));

        webTestClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreateDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() throws Exception {
        webTestClient.delete()
                .uri("/api/books/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }



    private Flux<Book> getDbBooks() {
        return Flux.fromIterable(getBookList(getAuthorList(), getGenreList()));
    }

    private List<Author> getAuthorList() {
        return IntStream.range(1,4).boxed()
                .map(id -> new Author(id, authorsName[id-1]))
                .toList();
    }

    private List<Genre> getGenreList() {
        return IntStream.range(1,4).boxed()
                .map(id -> new Genre(id, genresTitle[id-1]))
                .toList();
    }

    private List<Book> getBookList(List<Author> authors, List<Genre> genres) {
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
