package otus.ru.example.model;

import java.util.Objects;

public class Question {

    private String question;

    private String firstAnswer;

    private String secondAnswer;

    private String thirdAnswer;

    private String correctAnswer;


    public Question(String question, String firstAnswer, String secondAnswer, String thirdAnswer, String correctAnswer) {
        this.question = question;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
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
        return Objects.equals(question, question1.question) && Objects.equals(firstAnswer, question1.firstAnswer)
                && Objects.equals(secondAnswer, question1.secondAnswer)
                && Objects.equals(thirdAnswer, question1.thirdAnswer)
                && Objects.equals(correctAnswer, question1.correctAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, firstAnswer, secondAnswer, thirdAnswer, correctAnswer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
