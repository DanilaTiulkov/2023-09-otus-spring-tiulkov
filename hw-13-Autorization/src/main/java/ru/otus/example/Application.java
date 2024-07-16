package ru.otus.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Application.class, args);
        System.out.println("Application started at http://localhost:8080");
    }
}
