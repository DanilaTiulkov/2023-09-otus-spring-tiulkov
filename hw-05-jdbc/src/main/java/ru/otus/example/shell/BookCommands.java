package ru.otus.example.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.example.converters.BookConverter;
import ru.otus.example.services.BookService;

import java.util.stream.Collectors;

@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @Autowired
    public BookCommands(BookService bookService, BookConverter bookConverter) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String getBookById(long id) {
        return bookService.findById(id).map(bookConverter::bookToString).
                orElse("Sorry, but this book is not in the database");
    }

    @ShellMethod(value = "Find all books", key = "fab")
    public String getBooks() {
        return bookService.findAll().stream().map(bookConverter::bookToString).
                collect(Collectors.joining("." + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert", key = "bins")
    public String createBook(String title, int authorId, int genreId) {
        var book = bookService.insert(title, authorId, genreId);
        return "Book added successfully\n".concat(bookConverter.bookToString(book));
    }

    @ShellMethod(value = "Delete book", key = "bdel")
    public String deleteBook(long id) {
        bookService.deleteById(id);
        return "Book deleted successfully";
    }

    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook (long id, String title, long authorId, long genreId) {
        var book = bookService.update(id, title, authorId, genreId);
        return "Book updated successfully.\n".concat(bookConverter.bookToString(book));
    }
}
