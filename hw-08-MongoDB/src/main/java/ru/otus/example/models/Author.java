package ru.otus.example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Objects;

@Document(collection = "authors")
public class Author {

    @Id
    private String authorId;

    private String fullName;


    public Author() {
    }

    public Author(String authorId, String fullName) {
        this.authorId = authorId;
        this.fullName = fullName;
    }


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
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
        return Objects.equals(authorId, author.authorId) && Objects.equals(fullName, author.fullName);
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
