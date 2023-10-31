package otus.ru.example.model;

import java.util.Objects;

public class Question {

    private int questionId;

    private String question;

    private int answerId;

    private String answer;

    private int correctAnswer;

    public Question(int questionId, String question, int answerId, String answer, int correctAnswer) {
        this.questionId = questionId;
        this.question = question;
        this.answerId = answerId;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
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
        return questionId == question1.questionId && answerId == question1.answerId
                && correctAnswer == question1.correctAnswer && Objects.equals(question, question1.question)
                && Objects.equals(answer, question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, question, answerId, answer, correctAnswer);
    }

    @Override
    public String toString() {
        return questionId + " " + question + " " + answer + " " + correctAnswer;
    }
}
