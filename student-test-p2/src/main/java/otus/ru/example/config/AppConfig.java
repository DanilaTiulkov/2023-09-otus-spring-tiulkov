package otus.ru.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class AppConfig implements TestConfig, TestFileNameProvider {

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
