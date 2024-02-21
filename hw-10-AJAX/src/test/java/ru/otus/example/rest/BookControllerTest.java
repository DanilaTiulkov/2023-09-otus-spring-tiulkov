package ru.otus.example.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;
import ru.otus.example.models.dto.BookCreateDto;
import ru.otus.example.models.dto.BookUpdateDto;
import ru.otus.example.services.AuthorService;
import ru.otus.example.services.BookService;
import ru.otus.example.services.GenreService;

import static org.hamcrest.Matchers.containsString;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

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

        this.mvc.perform(get("/api/books/1")).andDo(print())
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

        this.mvc.perform(get("/api/books")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Three planets")))
                .andExpect(content().string(containsString("In search of the lost")))
                .andExpect(content().string(containsString("Behind a closed door")));
    }



    @Test
    @DisplayName("Обновление книги")
    public void updateBook() throws Exception {
        var bookUpdateDto = new BookUpdateDto(1, "Two planets", 1L, 1L);

        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);

        this.mvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto)))
                        .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Удаление книги")
    public void deleteBook() throws Exception {
        this.mvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Сохранение книги")
    public void saveBook() throws Exception {
        var bookCreateDto = new BookCreateDto("Test book", 1L, 1L);
        this.mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().is(200));
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
