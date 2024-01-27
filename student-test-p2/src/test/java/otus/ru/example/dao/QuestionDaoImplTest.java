package otus.ru.example.dao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import otus.ru.example.config.AppConfig;
import otus.ru.example.config.TestFileNameProvider;
import otus.ru.example.domain.Question;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionDaoImplTest {

    private TestFileNameProvider testFileNameProvider;

    private QuestionDao qdi;

    @BeforeAll
    void init(){
        testFileNameProvider = new AppConfig(3, "QuestionsTest.csv");
        qdi = new QuestionDaoImpl(testFileNameProvider);
    }

    @DisplayName("Проверка количества вопросов внутри коллекции result")
    @Test
    void getQuestions() {
        List<Question> result = qdi.getQuestions();
        assertEquals(5, result.size());
    }
}
