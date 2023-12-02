package otus.ru.example.dao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import otus.ru.example.config.AppConfig;
import otus.ru.example.config.TestFileNameProvider;
import otus.ru.example.domain.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;



class CsvQuestionDaoTest {

    private TestFileNameProvider testFileNameProvider;

    private QuestionDao qdi;

    @DisplayName("Проверка количества вопросов внутри коллекции result")
    @Test
    void getQuestions() {
        Map<String, String> fileNameByLocaleTag = new HashMap<>();
        fileNameByLocaleTag.put("ru-RU", "QuestionsTest_ru.csv");
        fileNameByLocaleTag.put("en-US", "QuestionsTest.csv");
        testFileNameProvider = new AppConfig(3, "ru-RU", fileNameByLocaleTag);
        qdi = new CsvQuestionDao(testFileNameProvider);
        List<Question> result = qdi.getQuestions();
        assertEquals(5, result.size());
    }
}
