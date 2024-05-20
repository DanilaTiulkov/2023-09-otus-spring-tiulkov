package ru.otus.example.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class BookUpdateDto {

    @NotNull(message = "id cant be empty")
    private long bookId;

    @NotBlank(message = "title cant be empty")
    private String title;

    @NotNull(message = "author cant be empty")
    private Long authorId;

    @NotNull(message = "genre cant be empty")
    private Long genreId;


    public BookUpdateDto(long bookId, String title, Long authorId, Long genreId) {
        this.bookId = bookId;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
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
        BookUpdateDto that = (BookUpdateDto) o;
        return bookId == that.bookId && Objects.equals(title, that.title)
                && Objects.equals(authorId, that.authorId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, authorId, genreId);
    }

    @Override
    public String toString() {
        return "BookUpdateDto{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", genreId=" + genreId +
                '}';
    }
}
