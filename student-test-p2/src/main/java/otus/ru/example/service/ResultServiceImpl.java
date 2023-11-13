package otus.ru.example.service;

import org.springframework.stereotype.Service;
import otus.ru.example.config.TestConfig;
import otus.ru.example.domain.TestResult;

@Service("resultService")
public class ResultServiceImpl implements ResultService{

    private final IOService ioService;

    private final TestConfig testConfig;

    public ResultServiceImpl(TestConfig testConfig, IOService ioService) {
        this.testConfig = testConfig;
        this.ioService = ioService;
    }

    @Override
    public void showResult(TestResult testResult) {
        String studentName = testResult.getStudent().getFullName();
        int result = testResult.getRightAnswerCount();
        int answeredQuestionsCount = testResult.getAnsweredQuestions().size();
        int rightAnswerCountToPass = testConfig.getRightAnswerCountToPass();
        ioService.printFormattedLine("Student: %s", studentName);
        ioService.printFormattedLine("Answered questions count: %d", answeredQuestionsCount);
        ioService.printFormattedLine( "Result is: %d", result);
        ioService.printFormattedLine("Right answer count to pass: %d", rightAnswerCountToPass);

        if (result >= rightAnswerCountToPass) {
            ioService.printLine("Congratulation! You passed the test!");
            return;
        }
        ioService.printLine("Sorry. You fail the test.");
    }
}
