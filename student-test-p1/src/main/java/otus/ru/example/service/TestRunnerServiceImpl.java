package otus.ru.example.service;

import org.springframework.stereotype.Service;

@Service("testRunnerService")
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    public TestRunnerServiceImpl(TestService testService) {
        this.testService = testService;
    }

    @Override
    public void run() {
        testService.executeTest();
    }
}
