package ru.otus.example.converters;

import org.springframework.stereotype.Component;
import ru.otus.example.models.Comment;

@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Book id: %s.\n Comment id: %s\n Comment text: %s."
                .formatted(comment.getBook().getBookId(),
                        comment.getCommentId(),
                        comment.getcommentText());
    }
}
