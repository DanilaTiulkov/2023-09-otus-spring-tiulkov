package ru.otus.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.example.model.Butterfly;
import ru.otus.example.service.CaterpillarService;
import ru.otus.example.service.CaterpillarServiceImpl;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CaterpillarService caterpillarService = context.getBean(CaterpillarServiceImpl.class);
        List<Butterfly> butterflies = caterpillarService.start();
    }
}
