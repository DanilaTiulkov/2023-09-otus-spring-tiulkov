package otus.ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testRunnerService")
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Autowired
    public TestRunnerServiceImpl(TestService testService, StudentService studentService, ResultService resultService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
    }

    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
