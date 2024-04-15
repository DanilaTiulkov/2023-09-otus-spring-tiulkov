package ru.otus.example.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.BookDaoCustomImpl;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;
import ru.otus.example.models.dto.BookCreateDto;
import ru.otus.example.models.dto.BookUpdateDto;


@RestController
public class BookController {

    private final BookDaoCustomImpl bookDaoCustom;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookController(BookDaoCustomImpl bookDaoCustom, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDaoCustom = bookDaoCustom;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @GetMapping("/api/books")
    public Flux<Book> books() {
        return bookDaoCustom.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<Book> getBook(@PathVariable long id) {
        return bookDaoCustom.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book not found")));
    }

    @PutMapping("/api/books/{id}")
    public Mono<Book> editBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        var bookId = bookUpdateDto.getBookId();
        var bookTitle = bookUpdateDto.getTitle();
        var authorId = bookUpdateDto.getAuthorId();
        var genreId = bookUpdateDto.getGenreId();
        return genreDao.findById(genreId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genre not found")))
                .zipWith(authorDao.findById(authorId)
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("Author not found"))))
                .flatMap(response ->
                        bookDaoCustom.save(new Book(bookId, bookTitle, response.getT2(), response.getT1())));
    }

    @PostMapping("/api/books")
    public Mono<Book> saveBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        var bookTitle = bookCreateDto.getTitle();
        var authorId = bookCreateDto.getAuthorId();
        var genreId = bookCreateDto.getGenreId();
        return genreDao.findById(genreId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genre not found")))
                .zipWith(authorDao.findById(authorId)
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("Author not found"))))
                .flatMap(response -> bookDaoCustom.save(new Book(0, bookTitle, response.getT2(), response.getT1())));
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBook(@PathVariable long id) {
        return bookDaoCustom.deleteById(id);
    }
}
