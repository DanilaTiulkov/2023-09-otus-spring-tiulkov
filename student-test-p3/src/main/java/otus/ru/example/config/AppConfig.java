package otus.ru.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("otus.ru.example")// Какая разница между аннотациями в этом классе, этой же аннотацией, но над классом Main?
@PropertySource("classpath:application.properties")
public class AppConfig implements TestConfig, TestFileNameProvider { // Зачем использовать два интерфейса, если все данные можно поместить в TestConfig?

    private final int rightAnswerCountToPass;

    private final String testFileName;

    public AppConfig(@Value("${test.rightAnswersCountToPass}") int rightAnswerCountToPass,
                     @Value("${test.fileName}") String testFileName) {
        this.rightAnswerCountToPass = rightAnswerCountToPass;
        this.testFileName = testFileName;
    }

    @Override
    public int getRightAnswerCountToPass() {
        return rightAnswerCountToPass;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }
}
