package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.BookDto;
import ru.otus.example.model.mongo.BookDoc;

import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private  final AuthorService authorService;

    private final GenreService genreService;

    private Map<String, Long> authorsIds;

    private Map<String, Long> genresIds;


    public BookServiceImpl(AuthorService authorService, GenreService genreService,
                           Map<String, Long> authorsIds, Map<String, Long> genresIds) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.authorsIds = authorsIds;
        this.genresIds = genresIds;
    }

    @Override
    public BookDto transform(BookDoc bookDoc) {
        if (authorsIds.isEmpty() && genresIds.isEmpty()) {
            initCollections();
        }
        long authorId = authorsIds.get(bookDoc.getAuthor().getAuthorId());
        long genreId = genresIds.get(bookDoc.getGenre().getGenreId());
        return new BookDto(0, bookDoc.getTitle(), authorId, genreId);
    }

    private void initCollections() {
        authorsIds = authorService.findAllAuthorsIds();
        genresIds = genreService.findAllGenresIds();
    }
}
