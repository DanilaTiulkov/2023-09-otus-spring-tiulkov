package ru.otus.example.model.dto;


import java.util.Objects;

public class AuthorDto {

    private long authorId;

    private String authorDocId;

    private String fullName;

    public AuthorDto(long authorId, String authorDocId, String fullName) {
        this.authorId = authorId;
        this.authorDocId = authorDocId;
        this.fullName = fullName;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorDocId() {
        return authorDocId;
    }

    public void setAuthorDocId(String authorDocId) {
        this.authorDocId = authorDocId;
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
        AuthorDto authorDto = (AuthorDto) o;
        return authorId == authorDto.authorId && Objects.equals(authorDocId, authorDto.authorDocId)
                && Objects.equals(fullName, authorDto.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorDocId, fullName);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "authorId=" + authorId +
                ", authorDocId='" + authorDocId + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
