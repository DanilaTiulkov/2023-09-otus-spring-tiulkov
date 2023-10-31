package otus.ru.example.service;


import otus.ru.example.dao.QuestionDao;
import otus.ru.example.model.Question;
import java.util.List;

public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    private final IOService ioService;

    public TestServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public void executeTest() {
        List<Question> questions = questionDao.getQuestions();
        for (int count = 0 ; count < questions.size(); count++) {
            int questionId = questions.get(count).getQuestionId();
            String question = questions.get(count).getQuestion();
            int answerId = questions.get(count).getAnswerId();
            String answer = questions.get(count).getAnswer();
            if (count == 0 || !questions.get(count - 1).getQuestion().equals(question)) {
                ioService.printFormattedLine("%d.%s\n %d.%s", questionId, question, answerId, answer);
            } else {
                ioService.printFormattedLine(" %d.%s", answerId, answer);
            }
        }
    }
}
