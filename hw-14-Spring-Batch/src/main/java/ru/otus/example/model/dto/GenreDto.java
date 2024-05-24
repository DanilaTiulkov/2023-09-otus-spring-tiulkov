package ru.otus.example.model.dto;

import java.util.Objects;

public class GenreDto {

    private long genreId;

    private String genreDocId;

    private String genreName;


    public GenreDto(long genreId, String genreDocId, String genreName) {
        this.genreId = genreId;
        this.genreDocId = genreDocId;
        this.genreName = genreName;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public String getGenreDocId() {
        return genreDocId;
    }

    public void setGenreDocId(String genreDocId) {
        this.genreDocId = genreDocId;
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
        GenreDto genreDto = (GenreDto) o;
        return genreId == genreDto.genreId && Objects.equals(genreDocId, genreDto.genreDocId)
                && Objects.equals(genreName, genreDto.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, genreDocId, genreName);
    }

    @Override
    public String toString() {
        return "GenreDto{" +
                "genreId=" + genreId +
                ", genreDocId='" + genreDocId + '\'' +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
