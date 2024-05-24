package ru.otus.example.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "genres")
public class GenreDoc {

    @Id
    private String genreId;

    private String genreName;


    public GenreDoc() {
    }

    public GenreDoc(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }


    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenreDoc genre = (GenreDoc) o;
        return Objects.equals(genreId, genre.genreId) && Objects.equals(genreName, genre.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, genreName);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
