package otus.ru.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.ru.example.dao.QuestionDao;
import otus.ru.example.domain.Answer;
import otus.ru.example.domain.Question;
import otus.ru.example.domain.Student;
import otus.ru.example.domain.TestResult;

import java.util.List;

@Service("testService")
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    private final LocalizedIOService localizedIOService;

    @Autowired
    public TestServiceImpl(QuestionDao questionDao, LocalizedIOService localizedIOService) {
        this.questionDao = questionDao;
        this.localizedIOService = localizedIOService;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        List<Question> questions = questionDao.getQuestions();
        var testResult = new TestResult(student);
        questionsEnumeration(questions, testResult);
        return testResult;
    }

    private void questionsEnumeration(List<Question> questions, TestResult testResult){
        for (Question question : questions) {
            String questionText = question.text();
            List<Answer> answers = question.answers();
            localizedIOService.printFormattedLine(questionText);
            answers.forEach(answer -> localizedIOService.printLine(" " + answer.text()));
            acceptAnswer(question, testResult);
        }
    }

    private void acceptAnswer(Question question, TestResult testResult){
        boolean isCorrectAnswer;
        String answerText = localizedIOService.readStringWithPromptLocalized("TestService.answer.the.questions");
        isCorrectAnswer = checkAnswer(answerText, question.answers());
        testResult.applyAnswer(question, isCorrectAnswer);
    }

    private boolean checkAnswer(String answerText, List<Answer> answers) {
        for (Answer answer : answers) {
            if (answerText.equalsIgnoreCase(answer.text())) {
                return answer.isCorrect();
            }
        }
        return false;
    }
}
