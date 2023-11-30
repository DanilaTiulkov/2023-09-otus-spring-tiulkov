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
        questionsEnumeration(questions, testResult);
        return testResult;
    }

    private void questionsEnumeration(List<Question> questions, TestResult testResult){
        for (Question question : questions) {
            String questionText = question.text();
            List<Answer> answers = question.answers();
            ioService.printFormattedLine(questionText);
            answers.forEach(answer -> ioService.printLine(" " + answer.text()));
            acceptAnswer(question, testResult);
        }
    }

    private void acceptAnswer(Question question, TestResult testResult){
        boolean isCorrectAnswer;
        String answerText = ioService.readStringWithPrompt("Select answer");
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
