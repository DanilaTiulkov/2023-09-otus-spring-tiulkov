package otus.ru.example.dao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import otus.ru.example.domain.Question;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvQuestionDaoTest {

    @Autowired
    private QuestionDao qdi;

    @DisplayName("Проверка количества вопросов внутри коллекции result")
    @Test
    void getQuestions() {
        List<Question> result = qdi.getQuestions();
        assertEquals(5, result.size());
    }
}
