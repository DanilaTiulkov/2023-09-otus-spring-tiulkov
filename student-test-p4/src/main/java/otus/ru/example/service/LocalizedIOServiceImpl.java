package otus.ru.example.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("localizedIOServiceImpl")
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final IOService ioService;

    private final LocalizedMessagesService localizedMessagesService;

    public LocalizedIOServiceImpl(@Qualifier("streamIoService") IOService ioService,
                                  LocalizedMessagesService localizedMessagesService) {
        this.ioService = ioService;
        this.localizedMessagesService = localizedMessagesService;
    }

    @Override
    public void printLine(String s) {
        ioService.printLine(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        ioService.printFormattedLine(s, args);
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        return ioService.readStringWithPrompt(prompt);
    }

    @Override
    public String readString() {
        return ioService.readString();
    }

    @Override
    public void printLineLocalized(String code) {
        ioService.printLine(localizedMessagesService.getMessage(code));
    }

    @Override
    public void printFormatedLineLocalized(String code, Object... args) {
        ioService.printLine(localizedMessagesService.getMessage(code, args));
    }

    @Override
    public String readStringWithPromptLocalized(String promptCode) {
        return ioService.readStringWithPrompt(localizedMessagesService.getMessage(promptCode));
    }

    @Override
    public String getMessage(String code, Object... args) {
        return null;
    }
}
