package ru.otus.example.model.h2.temp;

import java.util.Objects;

public class GenreTemp {

    private long id;


    private String genreDocId;


    private long genreId;

    public GenreTemp(long id, String genreDocId, long genreId) {
        this.id = id;
        this.genreDocId = genreDocId;
        this.genreId = genreId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenreDocId() {
        return genreDocId;
    }

    public void setGenreDocId(String genreDocId) {
        this.genreDocId = genreDocId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenreTemp genreTemp = (GenreTemp) o;
        return id == genreTemp.id && genreId == genreTemp.genreId && Objects.equals(genreDocId, genreTemp.genreDocId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genreDocId, genreId);
    }

    @Override
    public String toString() {
        return "GenreTemp{" +
                "id=" + id +
                ", genreDocId='" + genreDocId + '\'' +
                ", genreId=" + genreId +
                '}';
    }
}
