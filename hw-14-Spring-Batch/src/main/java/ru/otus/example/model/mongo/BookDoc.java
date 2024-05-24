package ru.otus.example.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "books")
public class BookDoc {

    @Id
    private String bookId;

    private String title;

    @DBRef
    private AuthorDoc author;

    @DBRef
    private GenreDoc genre;


    public BookDoc() {
    }

    public BookDoc(String bookId, String title, AuthorDoc author, GenreDoc genre) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorDoc getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDoc author) {
        this.author = author;
    }

    public GenreDoc getGenre() {
        return genre;
    }

    public void setGenre(GenreDoc genre) {
        this.genre = genre;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookDoc book = (BookDoc) o;
        return Objects.equals(bookId, book.bookId) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                '}';
    }
}
