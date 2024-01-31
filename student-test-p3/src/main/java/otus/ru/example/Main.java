package otus.ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import otus.ru.example.config.AppConfig;
import otus.ru.example.service.TestRunnerService;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        TestRunnerService testRunnerService = context.getBean("testRunnerService", TestRunnerService.class);
        testRunnerService.run();
    }
}
