package ru.otus.example.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;
import ru.otus.example.models.dto.BookCreateDto;
import ru.otus.example.models.dto.BookUpdateDto;
import ru.otus.example.services.BookService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public List<Book> books() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Book getBook(@PathVariable long id) {
        var book = bookService.findById(id);
        return book.orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @PutMapping("/api/books/{id}")
    public void editBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        var bookId = bookUpdateDto.getBookId();
        var bookTitle = bookUpdateDto.getTitle();
        var authorId = bookUpdateDto.getAuthorId();
        var genreId = bookUpdateDto.getGenreId();
        bookService.update(bookId, bookTitle, authorId, genreId);
    }

    @PostMapping("/api/books")
    public void saveBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        var bookTitle = bookCreateDto.getTitle();
        var authorId = bookCreateDto.getAuthorId();
        var genreId = bookCreateDto.getGenreId();
        bookService.insert(bookTitle, authorId, genreId);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }
}
