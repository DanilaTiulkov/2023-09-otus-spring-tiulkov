package otus.ru.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;
import java.util.Map;


@ConfigurationProperties (prefix = "test")
public class AppConfig implements TestConfig, TestFileNameProvider, LocaleConfig {

    private final int rightAnswersCountToPass;

    private final Locale locale;

    private final Map<String, String> fileNameByLocaleTag;

    @ConstructorBinding
    public AppConfig(@Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass,
                     @Value("${test.locale}") String locale,
                     @Value("{test.fileNameByLocaleTag}") Map<String, String> fileNameByLocaleTag) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = Locale.forLanguageTag(locale);
        this.fileNameByLocaleTag = fileNameByLocaleTag;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public int getRightAnswerCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}
