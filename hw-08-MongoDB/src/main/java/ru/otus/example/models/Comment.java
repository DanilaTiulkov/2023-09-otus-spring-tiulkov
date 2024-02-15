package ru.otus.example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "comments")
public class Comment {

    @Id
    private String commentId;

    private String commentText;

    @DBRef
    private Book book;


    public Comment(String commentId, String commentText, Book book) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.book = book;
    }

    public Comment() {
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getcommentText() {
        return commentText;
    }

    public void setcommentText(String commentText) {
        this.commentText = commentText;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId) && Objects.equals(commentText, comment.commentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", text='" + commentText + '\'' +
                '}';
    }
}
