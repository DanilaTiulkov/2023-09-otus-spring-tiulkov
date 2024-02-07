package otus.ru.example.exceptions;

public class QuestionReadException extends RuntimeException {
    public QuestionReadException(String message) {
        super(message);
    }

    public QuestionReadException (String message, Throwable ex) {
        super(message, ex);
    }
}
