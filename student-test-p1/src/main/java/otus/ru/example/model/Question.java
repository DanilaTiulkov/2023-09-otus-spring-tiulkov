package otus.ru.example.model;

import java.util.Objects;

public class Question {

    private final int id;

    private final String question;

    private final String answer;

    private final int correctAnswer;

    public Question(int id, String question, String answer, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getcorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question1 = (Question) o;
        return id == question1.id && correctAnswer == question1.correctAnswer
                && Objects.equals(question, question1.question) && Objects.equals(answer, question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, answer, correctAnswer);
    }

    @Override
    public String toString() {
        return id + " " + question + " " + answer + " " + correctAnswer;
    }
}
