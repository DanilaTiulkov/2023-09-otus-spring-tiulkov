package ru.otus.example.model.h2.temp;

import java.util.Objects;

public class AuthorTemp {


    private long id;

    private String authorDocId;

    private long authorId;

    public AuthorTemp(long id, String authorDocId, long authorId) {
        this.id = id;
        this.authorDocId = authorDocId;
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorDocId() {
        return authorDocId;
    }

    public void setAuthorDocId(String authorDocId) {
        this.authorDocId = authorDocId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorTemp that = (AuthorTemp) o;
        return id == that.id && authorId == that.authorId && Objects.equals(authorDocId, that.authorDocId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorDocId, authorId);
    }

    @Override
    public String toString() {
        return "AuthorTemp{" +
                "id=" + id +
                ", authorDocId='" + authorDocId + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
