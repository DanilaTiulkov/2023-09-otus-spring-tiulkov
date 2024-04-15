package ru.otus.example.models.dto;

import ru.otus.example.models.Book;

public record CommentDto(long commentId,
                         String commentText,
                         Book book) {
}
