package ru.otus.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageBookController {


    @GetMapping({"/", "/books"})
    public String booksPage() {
        return "books";
    }

    @GetMapping("/book")
    public String bookPage(@RequestParam("bookId") long id, Model model) {
        model.addAttribute("bookId", id);
        return "book";
    }

    @GetMapping("/book/new")
    public String newBook(Model model) {
        model.addAttribute("refer", "/book/new");
        return "editBook";
    }

    @GetMapping("/books/book/edit/{id}")
    public String editBook(@PathVariable long id, Model model) {
        model.addAttribute("refer", "/books/book/edit");
        model.addAttribute("bookId", id);
        return "editBook";
    }
}
