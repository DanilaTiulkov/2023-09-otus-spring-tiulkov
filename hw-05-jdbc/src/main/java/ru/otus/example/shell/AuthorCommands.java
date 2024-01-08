package ru.otus.example.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.example.converters.AuthorConverter;
import ru.otus.example.services.AuthorService;

import java.util.stream.Collectors;

@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @Autowired
    public AuthorCommands(AuthorService authorService, AuthorConverter authorConverter) {
        this.authorService = authorService;
        this.authorConverter = authorConverter;
    }

    @ShellMethod(value = "Find authors", key = "fa")
    public String getAuthors() {
        return authorService.findAllAuthors().
                stream().map(authorConverter::authorToString).
                collect(Collectors.joining());
    }
}
