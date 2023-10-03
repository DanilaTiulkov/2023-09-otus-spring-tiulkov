package otus.ru.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import otus.ru.example.service.DataReaderService;


public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        DataReaderService dataReaderService = context.getBean("dataReaderServiceImpl", DataReaderService.class);
        dataReaderService.print();

    }
}
