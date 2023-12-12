package otus.ru.example.service;

public interface LocalizedMessagesService {
    String getMessage(String code, Object...args);
}
