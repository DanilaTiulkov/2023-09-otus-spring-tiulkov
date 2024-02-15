package ru.otus.example.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.example.controllers.BookController;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;
import ru.otus.example.models.dto.BookUpdateDto;
import ru.otus.example.services.AuthorService;
import ru.otus.example.services.BookServiceImpl;
import ru.otus.example.services.GenreService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookDaoTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private List<Book> dbBooks;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private String[] authorsName;

    private String[] genresTitle;

    private String[] booksTitle;


    @BeforeEach
    public void init() {
        authorsName = getAuthorsName();
        genresTitle = getGenresTitle();
        booksTitle = getBooksTitle();
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @Test
    @DisplayName("Поиск книги по id")
    public void findByid() throws Exception {
        var book = new Book(1, "Three planets",
                   new Author(1, "Ivan Sergeevich"),
                   new Genre(1, "Fantastic"));
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        this.mvc.perform(get("/book").param("bookId", "1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Three planets")))
                .andExpect(content().string(containsString("Ivan Sergeevich")))
                .andExpect(content().string(containsString("Fantastic")));
    }

    @Test
    @DisplayName("Поиск всех книг")
    public void findBooks() throws Exception {
        List<Book> books = dbBooks;
        when(bookService.findAll()).thenReturn(books);
        this.mvc.perform(get("/books")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Three planets")))
                .andExpect(content().string(containsString("In search of the lost")))
                .andExpect(content().string(containsString("Behind a closed door")));;
    }

    @Test
    @DisplayName("Получение формы для обновления книги")
    public void getUpdateBookForm() throws Exception {
        var book = new Book(1, "Three planets",
                new Author(1, "Ivan Sergeevich"),
                new Genre(1, "Fantastic"));

        when(bookService.findById(1)).thenReturn(Optional.of(book));
        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);

        this.mvc.perform(get("/book/edit/1")).andDo(print())
                .andExpect(content().string(containsString(
                        "name=\"bookId\" readonly=\"readonly\" value=\"1\"/>")))
                .andExpect(content().string(containsString(
                        "name=\"title\" value=\"Three planets\"")))
                .andExpect(content().string(containsString(
                        "<option value=\"1\" selected=\"selected\">Ivan Sergeevich</option>")))
                .andExpect(content().string(containsString(
                        "<option value=\"1\" selected=\"selected\">Fantastic</option>")));
    }

    @Test
    @DisplayName("Обновление книги")
    public void updateBook() throws Exception {
        var bookUpdateDto = new BookUpdateDto(1, "Three planets", 1L, 1L);

        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);

        this.mvc.perform(post("/book/edit")
                    .param("bookId", String.valueOf(bookUpdateDto.getBookId()))
                    .param("title", bookUpdateDto.getTitle())
                    .param("authorId", String.valueOf(bookUpdateDto.getAuthorId()))
                    .param("genreId", String.valueOf(bookUpdateDto.getGenreId())))
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() throws Exception {
        this.mvc.perform(post("/book/delete/1"))
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Получение формы для сохранения кнмгм")
    public void getSaveBookForm() throws Exception {
        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);
        this.mvc.perform(get("/book/new")).andDo(print())
                .andExpect(content().string(containsString(
                        "<input id=\"input-title\" type=\"text\" name=\"title\" value=\"\"/>")))
                .andExpect(content().string(containsString(
                        "<option value=\"\">Choose an author</option>")))
                .andExpect(content().string(containsString(
                        "<option value=\"\">Choose a genre</option>")));
    }

    @Test
    @DisplayName("Сохранение книги")
    public void saveBook() throws Exception {
        this.mvc.perform(post("/book/new")
                        .param("title", "Test book")
                        .param("authorId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is(302));
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
