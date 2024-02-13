package ru.otus.example.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.example.converters.CommentConverter;
import ru.otus.example.models.Comment;
import ru.otus.example.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class CommentCommand {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @Autowired
    public CommentCommand(CommentService commentService, CommentConverter commentConverter) {
        this.commentService = commentService;
        this.commentConverter = commentConverter;
    }

    @ShellMethod(value = "Find comments by book id", key = "fcbbid")
    public String findCommentsByBookId(long bookId) {
        List<Comment> comments = commentService.findCommentsByBookId(bookId);
        if (comments.isEmpty()) {
            return "Book hasn't comments";
        }
        return comments.stream().map(commentConverter::commentToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "fcbid")
    public String findCommentById(long commentId) {
        return commentService.findCommentById(commentId).map(commentConverter::commentToString)
                .orElse("Sorry comment doesn't found");
    }

    @ShellMethod(value = "Insert comment", key = "ic")
    public String createComment(String text, long bookId) {
        var comment = commentService.insertComment(text, bookId);
        return commentConverter.commentToString(comment);
    }

    @ShellMethod(value = "Update comment", key = "uc")
    public String updateComment(long commentId, String commentText) {
        var comment = commentService.updateComment(commentId, commentText);
        return "Comment has been updated";
    }

    @ShellMethod(value = "Delete comment", key = "dc")
    public String deleteComment(long commentId) {
        commentService.deleteComment(commentId);
        return "Comment has been deleted";
    }
}
