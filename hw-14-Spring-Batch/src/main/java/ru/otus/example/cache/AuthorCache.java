package ru.otus.example.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthorCache {

    private final Map<String, Long> authorsIds;

    public AuthorCache() {
        this.authorsIds = new ConcurrentHashMap<>();
    }

    public void addAuthorIds(String authorDocId, long authorId) {
        authorsIds.put(authorDocId, authorId);
    }

    public Map<String, Long> getAuthorsIds() {
        return authorsIds;
    }
}
