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
        questions.forEach(question -> ioService.printLine(question.toString()));
    }
}
