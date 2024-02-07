package ru.otus.example.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Comments")
@NamedEntityGraph(name = "comment-entity-graph",
        attributeNodes = @NamedAttributeNode("book"))
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(name = "comment_text")
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


    public Comment(long commentId, String commentText, Book book) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.book = book;
    }

    public Comment() {
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId && Objects.equals(commentText, comment.commentText);
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
