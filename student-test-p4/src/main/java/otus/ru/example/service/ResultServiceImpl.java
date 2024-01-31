package otus.ru.example.service;

import org.springframework.stereotype.Service;
import otus.ru.example.config.TestConfig;
import otus.ru.example.domain.TestResult;

@Service("resultService")
public class ResultServiceImpl implements ResultService {

    private final LocalizedIOService localizedIOService;

    private final TestConfig testConfig;

    public ResultServiceImpl(TestConfig testConfig, LocalizedIOService localizedIOService) {
        this.testConfig = testConfig;
        this.localizedIOService = localizedIOService;
    }

    @Override
    public void showResult(TestResult testResult) {
        String studentName = testResult.getStudent().getFullName();
        int result = testResult.getRightAnswerCount();
        int answeredQuestionsCount = testResult.getAnsweredQuestions().size();
        int rightAnswerCountToPass = testConfig.getRightAnswerCountToPass();
        localizedIOService.printLineLocalized("ResultServise.test.results");
        localizedIOService.printFormatedLineLocalized("ResultService.student", studentName);
        localizedIOService.printFormatedLineLocalized("ResultService.answered.questions.count", answeredQuestionsCount);
        localizedIOService.printFormatedLineLocalized("ResultService.result.is", result);
        localizedIOService.printFormatedLineLocalized("ResultService.right.answer.count.to.pass",
                                                  rightAnswerCountToPass);

        if (result >= rightAnswerCountToPass) {
            localizedIOService.printLineLocalized("ResultService.passed.test");
            return;
        }
        localizedIOService.printLineLocalized("ResultService.fail.test");
    }
}
