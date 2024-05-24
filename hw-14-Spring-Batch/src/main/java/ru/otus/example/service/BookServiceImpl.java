package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.cache.AuthorCache;
import ru.otus.example.cache.GenreCache;
import ru.otus.example.model.dto.BookDto;
import ru.otus.example.model.mongo.BookDoc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookServiceImpl implements BookService {

    private  final AuthorCache authorCache;

    private final GenreCache genreCache;

    private Map<String, Long> authorsIds;

    private Map<String, Long> genresIds;


    public BookServiceImpl(AuthorCache authorCache, GenreCache genreCache) {
        this.authorCache = authorCache;
        this.genreCache = genreCache;
        this.authorsIds = new ConcurrentHashMap<>();
        this.genresIds = new ConcurrentHashMap<>();
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
        authorsIds = authorCache.getAuthorsIds();
        genresIds = genreCache.getGenresIds();
    }
}
