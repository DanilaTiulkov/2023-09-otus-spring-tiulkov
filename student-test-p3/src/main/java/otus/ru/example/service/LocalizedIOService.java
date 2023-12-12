package otus.ru.example.service;

public interface LocalizedIOService extends LocalizedMessagesService, IOService {
    void printLineLocalized(String code);

    void printFormatedLineLocalized(String code, Object...args);

    String readStringWithPromptLocalized(String promptCode);
}
