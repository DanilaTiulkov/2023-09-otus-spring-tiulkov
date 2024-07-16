package ru.otus.example.converters;

import org.springframework.stereotype.Component;
import ru.otus.example.models.Comment;

@Component
public class CommentConverter {


    public String commentToString(Comment comment) {
        return "Book id: %d.\n Comment id: %d\n Comment text: %s.\n"
                .formatted(comment.getBook().getBookId(),
                        comment.getCommentId(),
                        comment.getcommentText());
    }
}
