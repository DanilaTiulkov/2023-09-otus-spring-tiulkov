package otus.ru.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import otus.ru.example.service.TestRunnerService;


@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        TestRunnerService testRunnerService = context.getBean("testRunnerService", TestRunnerService.class);
        testRunnerService.run();
    }
}
