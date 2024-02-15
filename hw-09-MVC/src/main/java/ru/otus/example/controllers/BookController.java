package ru.otus.example.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.example.models.dto.BookCreateDto;
import ru.otus.example.models.dto.BookUpdateDto;
import ru.otus.example.models.Book;
import ru.otus.example.services.AuthorService;
import ru.otus.example.services.BookService;
import ru.otus.example.services.GenreService;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping({"/", "/books"})
    public String booksPage(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String bookPage(@RequestParam("bookId") long id, Model model) {
        var book = bookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/book/new")
    public String newBook(Model model) {
        var book = new BookCreateDto("",null, null);
        model.addAttribute("book", book);
        model.addAttribute("refer", "/book/new");
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "editBook";
    }

    @GetMapping("/book/edit/{id}")
    public String editBook(@PathVariable long id, Model model) {
        var book = bookService.findById(id)
                .map(b -> new BookUpdateDto(
                        b.getBookId(),
                        b.getTitle(),
                        b.getAuthor().getAuthorId(),
                        b.getGenre().getGenreId()))
                .orElseThrow(() -> new ru.otus.example.exceptions.EntityNotFoundException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("refer", "/book/edit");
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "editBook";
    }

    @PostMapping("/book/edit")
    public String editBook(@Valid @ModelAttribute("book") BookUpdateDto bookUpdateDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("refer", "/book/edit");
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "editBook";
        }
        var bookId = bookUpdateDto.getBookId();
        var title = bookUpdateDto.getTitle();
        var authorId = bookUpdateDto.getAuthorId();
        var genreId = bookUpdateDto.getGenreId();
        bookService.update(bookId, title, authorId, genreId);
        return "redirect:/books";
    }

    @PostMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/book/new")
    public String createBook(@Valid @ModelAttribute("book") BookCreateDto bookCreateDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("refer", "/book/new");
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "editBook";
        }
        var title = bookCreateDto.getTitle();
        var authorId = bookCreateDto.getAuthorId();
        var genreId = bookCreateDto.getGenreId();
        bookService.insert(title, authorId, genreId);
        return "redirect:/books";
    }
}
