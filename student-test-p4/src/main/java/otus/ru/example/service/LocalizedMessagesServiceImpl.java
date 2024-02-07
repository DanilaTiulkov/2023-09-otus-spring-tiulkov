package otus.ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import otus.ru.example.config.LocaleConfig;

@Service("localizedMessagesService")
public class LocalizedMessagesServiceImpl implements LocalizedMessagesService {

    private final LocaleConfig localeConfig;

    private final MessageSource messageSource;

    @Autowired
    public LocalizedMessagesServiceImpl(LocaleConfig localeConfig, MessageSource messageSource) {
        this.localeConfig = localeConfig;
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code, Object...args) {
        return messageSource.getMessage(code, args, localeConfig.getLocale());
    }
}
