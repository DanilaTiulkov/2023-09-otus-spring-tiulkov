package otus.ru.example.dao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.ru.example.model.Question;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoImplTest {
    @DisplayName("Проверка количества вопросов внутри коллекции result")
    @Test
    void getQuestions() {
        QuestionDaoImpl qdi = new QuestionDaoImpl("QuestionsTest.csv");
        List<Question> result = qdi.getQuestions();
        assertEquals(15, result.size());





    }
}