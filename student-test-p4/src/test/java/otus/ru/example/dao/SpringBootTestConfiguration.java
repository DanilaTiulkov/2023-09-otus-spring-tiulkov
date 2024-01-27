package otus.ru.example.dao;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import otus.ru.example.config.AppConfig;

@ComponentScan({"otus.ru.example.dao", "otus.ru.example.config", "otus.ru.example.service"})
@EnableConfigurationProperties(AppConfig.class)
@SpringBootConfiguration
public class SpringBootTestConfiguration {
}
