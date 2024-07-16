package ru.otus.example.model.h2;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorId;

    @Column(name = "full_name")
    private String fullName;


    public Author() {
    }

    public Author(long authorId, String fullName) {
        this.authorId = authorId;
        this.fullName = fullName;
    }


    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return authorId == author.authorId && Objects.equals(fullName, author.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, fullName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
