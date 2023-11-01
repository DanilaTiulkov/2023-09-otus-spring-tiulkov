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
        for (int count = 1; count < questions.size(); count++) {
            String question = questions.get(count).getQuestion();
            String firstAnswer = questions.get(count).getFirstAnswer();
            String secondAnswer = questions.get(count).getSecondAnswer();
            String thirdAnswer = questions.get(count).getThirdAnswer();
            String correctAnswer = questions.get(count).getCorrectAnswer();
            ioService.printFormattedLine("%d.%s\n %s\n %s\n %s",
                    count, question, firstAnswer, secondAnswer, thirdAnswer);
        }
    }
}
