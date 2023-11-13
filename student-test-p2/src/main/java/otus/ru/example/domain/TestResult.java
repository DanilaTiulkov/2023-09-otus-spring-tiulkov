package otus.ru.example.domain;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private final Student student;

    private final List<Question> answeredQuestions;

    private int rightAnswerCount;

    public TestResult(Student student) {
        this.student = student;
        this.answeredQuestions = new ArrayList<>();
    }

    public void applyAnswer(Question question, boolean isRightAnswer) {
        answeredQuestions.add(question);
        if(isRightAnswer) {
            rightAnswerCount++;
        }
    }

    public Student getStudent() {
        return student;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public int getRightAnswerCount() {
        return rightAnswerCount;
    }
}
