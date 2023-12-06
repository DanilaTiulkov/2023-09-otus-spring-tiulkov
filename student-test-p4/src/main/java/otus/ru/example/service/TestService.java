package otus.ru.example.service;

import otus.ru.example.domain.Student;
import otus.ru.example.domain.TestResult;

public interface TestService {

    TestResult executeTestFor(Student student);
}
