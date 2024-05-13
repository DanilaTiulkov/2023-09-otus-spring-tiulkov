package ru.otus.example.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class BookCreateDto {

    @NotBlank(message = "wrong title")
    private String title;

    @NotNull(message = "wrong author")
    private Long authorId;

    @NotNull(message = "wrong genre")
    private Long genreId;

    public BookCreateDto(String title, Long authorId, Long genreId) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
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
        BookCreateDto that = (BookCreateDto) o;
        return Objects.equals(title, that.title)
                && Objects.equals(authorId, that.authorId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authorId, genreId);
    }

    @Override
    public String toString() {
        return "BookCreateDto{" +
                "title='" + title + '\'' +
                ", authorId=" + authorId +
                ", genreId=" + genreId +
                '}';
    }
}
