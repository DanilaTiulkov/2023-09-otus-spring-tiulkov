package otus.ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.ru.example.domain.Student;

@Service("studentService")
public class StudentServiceImpl implements StudentService{

    private final IOService ioService;

    @Autowired
    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student determineCurrentStudent() {
        var firstname = ioService.readStringWithPrompt("Please input your first name");
        var lastname = ioService.readStringWithPrompt("Please input your last name");
        return new Student(firstname, lastname);
    }
}
