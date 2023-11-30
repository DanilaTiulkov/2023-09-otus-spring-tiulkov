package otus.ru.example.dao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.ru.example.config.AppConfig;
import otus.ru.example.config.TestFileNameProvider;
import otus.ru.example.domain.Question;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



class CsvQuestionDaoTest {

    private TestFileNameProvider testFileNameProvider;

    private QuestionDao qdi;

    @DisplayName("Проверка количества вопросов внутри коллекции result")
    @Test
    void getQuestions() {
        testFileNameProvider = new AppConfig(3, "QuestionsTest.csv");
        qdi = new CsvQuestionDao(testFileNameProvider);
        List<Question> result = qdi.getQuestions();
        assertEquals(5, result.size());
    }
}
