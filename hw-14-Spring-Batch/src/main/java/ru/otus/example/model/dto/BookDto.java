package ru.otus.example.model.dto;

import java.util.Objects;

public class BookDto {

    private long bookId;

    private String title;

    private long authorId;

    private long genreId;


    public BookDto(long bookId, String title, long authorId, long genreId) {
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

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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
        BookDto that = (BookDto) o;
        return bookId == that.bookId && authorId == that.authorId
                && genreId == that.genreId && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, authorId, genreId);
    }

    @Override
    public String toString() {
        return "BookCreateDto{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", genreId=" + genreId +
                '}';
    }
}
