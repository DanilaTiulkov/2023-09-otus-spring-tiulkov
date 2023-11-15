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

    private final IOService ioService;

    @Autowired
    public TestServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        List<Question> questions = questionDao.getQuestions();
        var testResult = new TestResult(student);
        boolean isCorrectAnswer;
        for (Question question : questions) {
            String questionText = question.text();
            List<Answer> answers = question.answers();
            ioService.printFormattedLine(questionText);
            answers.forEach(answer -> ioService.printLine(" " + answer.text()));
            String answerText = ioService.readStringWithPrompt("Select answer");
            isCorrectAnswer = checkAnswer(answerText, answers);
            testResult.applyAnswer(question, isCorrectAnswer);
        }
        return testResult;
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
