package org.example.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan({"org.example.service", "org.example.mapper", "org.example.repository"})
public class SpringBootTestConfiguration {
}
