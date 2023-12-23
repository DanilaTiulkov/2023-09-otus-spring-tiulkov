package ru.otus.example.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.example.converters.GenreConverter;
import ru.otus.example.services.GenreService;

import java.util.stream.Collectors;

@ShellComponent
public class GenreCommands {

    private GenreService genreService;

    private GenreConverter genreConverter;

    @Autowired
    public GenreCommands(GenreService genreService, GenreConverter genreConverter) {
        this.genreService = genreService;
        this.genreConverter = genreConverter;
    }

    @ShellMethod(value = "Find genres", key = "fg")
    public String getGenres() {
        return genreService.findAll().stream().map(genreConverter::genreToString).collect(Collectors.joining());
    }
}
