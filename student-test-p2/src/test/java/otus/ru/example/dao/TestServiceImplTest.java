package otus.ru.example.dao;



import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.ru.example.domain.Answer;
import otus.ru.example.domain.Question;
import otus.ru.example.domain.Student;
import otus.ru.example.domain.TestResult;
import otus.ru.example.service.IOService;
import otus.ru.example.service.StreamsIOService;
import otus.ru.example.service.TestService;
import otus.ru.example.service.TestServiceImpl;
import java.util.ArrayList;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private IOService ioService;
    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;


    @Test
    @DisplayName("Возвращение объекта TestResult в методе executeTestFor с правильно отвеченным вопросом")
    void executeTestFor() {
        Student student = new Student("Иван", "Иванов");
        List<Answer> answers = new ArrayList<>();
        Question question = new Question("What is the capital of Great Britain",answers);
        answers.add(new Answer("London", true));
        answers.add(new Answer("Moscow", false));
        answers.add(new Answer("Dhaka", false));

        Mockito.when(questionDao.getQuestions()).thenReturn(List.of(question));
        Mockito.when(ioService.readStringWithPrompt("Select answer")).thenReturn("London");

        TestResult testResult = testService.executeTestFor(student);

        Assertions.assertEquals(1, testResult.getRightAnswerCount());
    }

}
