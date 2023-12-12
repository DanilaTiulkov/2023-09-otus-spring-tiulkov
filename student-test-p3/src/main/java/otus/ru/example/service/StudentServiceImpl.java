package otus.ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.ru.example.domain.Student;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService localizedIOService;

    @Autowired
    public StudentServiceImpl(LocalizedIOService localizedIOService) {
        this.localizedIOService = localizedIOService;
    }

    @Override
    public Student determineCurrentStudent() {
        var firstname = localizedIOService.readStringWithPromptLocalized("StudentService.input.first.name");
        var lastname = localizedIOService.readStringWithPromptLocalized("StudentService.input.last.name");
        return new Student(firstname, lastname);
    }
}
