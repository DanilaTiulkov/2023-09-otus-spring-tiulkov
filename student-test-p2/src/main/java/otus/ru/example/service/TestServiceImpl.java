package otus.ru.example.service;


import org.springframework.stereotype.Service;
import otus.ru.example.dao.QuestionDao;
import otus.ru.example.domain.Answer;
import otus.ru.example.domain.Question;
import java.util.List;

@Service("testService")
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
        for (int count = 0; count < questions.size(); count++) {
            Question question = questions.get(count);
            List<Answer> answers = question.answers();
            String questionText = question.text();
            ioService.printFormattedLine("%d.%s", count + 1, questionText);
            answers.forEach(answer -> ioService.printLine(" " + answer.text()));
        }
    }
}
