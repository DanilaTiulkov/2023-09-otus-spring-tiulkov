package otus.ru.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import otus.ru.example.config.AppConfig;
import otus.ru.example.service.TestRunnerService;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        TestRunnerService testRunnerService = context.getBean("testRunnerService", TestRunnerService.class);
        testRunnerService.run();
    }
}
