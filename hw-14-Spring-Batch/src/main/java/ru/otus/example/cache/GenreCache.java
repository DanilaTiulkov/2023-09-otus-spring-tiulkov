package ru.otus.example.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GenreCache {

    private final Map<String, Long> genresIds;

    public GenreCache() {
        this.genresIds = new ConcurrentHashMap<>();
    }

    public void addGenreIds(String genreDocId, long genreId) {
        genresIds.put(genreDocId, genreId);
    }

    public Map<String, Long> getGenresIds() {
        return genresIds;
    }
}
