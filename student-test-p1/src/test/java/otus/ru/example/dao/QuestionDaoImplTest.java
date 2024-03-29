package otus.ru.example.dao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.ru.example.domain.Question;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoImplTest {
    @DisplayName("Проверка количества вопросов внутри коллекции result")
    @Test
    void getQuestions() {
        CsvQuestionDao qdi = new CsvQuestionDao("QuestionsTest.csv");
        List<Question> result = qdi.getQuestions();
        assertEquals(5, result.size());
    }
}
