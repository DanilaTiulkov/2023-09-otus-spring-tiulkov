package ru.otus.example.models.dto;


public record BookDto(long bookId,
                      String title,
                      AuthorDto author,
                      GenreDto genre) {
}
