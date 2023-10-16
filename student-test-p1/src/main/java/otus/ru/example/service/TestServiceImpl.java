package otus.ru.example.service;


import otus.ru.example.dao.QuestionDao;
import otus.ru.example.model.Question;
import java.util.List;

public class TestServiceImpl implements TestService {

    private final QuestionDao reader;

    public TestServiceImpl(QuestionDao reader) {
        this.reader = reader;
    }

    @Override
    public void executeTest() {
        List<Question> result = reader.getQuestions();
        result.forEach(System.out::println);
    }
}
