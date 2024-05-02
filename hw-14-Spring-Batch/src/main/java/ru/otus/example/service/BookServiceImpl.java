package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.BookDto;
import ru.otus.example.model.h2.temp.AuthorTemp;
import ru.otus.example.model.h2.temp.GenreTemp;
import ru.otus.example.model.mongo.BookDoc;
import ru.otus.example.repository.AuthorTempRepository;
import ru.otus.example.repository.GenreTempRepository;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorTempRepository authorTempRepository;

    private final GenreTempRepository genreTempRepository;

    private final Map<String, Long> authorsTemp;

    private final Map<String, Long> genresTemp;


    public BookServiceImpl(Map<String, Long> authorsTemp, AuthorTempRepository authorTempRepository,
                           GenreTempRepository genreTempRepository, Map<String, Long> genresTemp) {
        this.authorsTemp = authorsTemp;
        this.authorTempRepository = authorTempRepository;
        this.genreTempRepository = genreTempRepository;
        this.genresTemp = genresTemp;
    }

    @Override
    public BookDto transform(BookDoc bookDoc) {
        if (authorsTemp.isEmpty() && genresTemp.isEmpty()) {
            initCollections();
        }
        long authorId = authorsTemp.get(bookDoc.getAuthor().getAuthorId());
        long genreId = genresTemp.get(bookDoc.getGenre().getGenreId());
        return new BookDto(0, bookDoc.getTitle(), authorId, genreId);
    }

    private void initCollections() {
        List<AuthorTemp> authorTempList = authorTempRepository.findAll();
        List<GenreTemp> genreTempList = genreTempRepository.findAll();
        authorTempList.forEach(authorTemp -> authorsTemp.put(authorTemp.getAuthorDocId(), authorTemp.getAuthorId()));
        genreTempList.forEach(genreTemp -> genresTemp.put(genreTemp.getGenreDocId(), genreTemp.getGenreId()));
    }
}
