package ru.otus.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.CommentDao;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println(context.getBean("authorDao", AuthorDao.class));
        System.out.println(context.getBean("bookDao", BookDao.class));
        System.out.println(context.getBean("commentDao", CommentDao.class));
    }
}
