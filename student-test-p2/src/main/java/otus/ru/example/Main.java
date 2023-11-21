package otus.ru.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import otus.ru.example.config.AppConfig;
import otus.ru.example.service.TestRunnerService;

@Configuration
@ComponentScan("otus.ru.example")
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        TestRunnerService testRunnerService = context.getBean("testRunnerService", TestRunnerService.class);
        testRunnerService.run();
    }
}
