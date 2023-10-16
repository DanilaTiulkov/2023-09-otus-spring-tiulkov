package otus.ru.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import otus.ru.example.service.TestRunnerService;


public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        TestRunnerService testRunnerService = context.getBean("testRunnerService", TestRunnerService.class);
        testRunnerService.run();

    }
}
