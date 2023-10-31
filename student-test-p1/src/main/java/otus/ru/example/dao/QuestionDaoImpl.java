package otus.ru.example.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import otus.ru.example.exceptions.QuestionReadException;
import otus.ru.example.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    public QuestionDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestions() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            CsvToBean<Question> csvToBean = new CsvToBeanBuilder<Question>(br)
                    .withType(Question.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            Iterator<Question> iterator = csvToBean.iterator();
            List<Question> questions = questionsIterator(iterator);
            return questions;
        } catch (IOException  ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

        public List<Question> questionsIterator(Iterator<Question> iterator) {
        List<Question> questions = new ArrayList<>();
        try {
            while (iterator.hasNext()) {
                Question iteratorQuestion = iterator.next();
                int questionId = iteratorQuestion.getQuestionId();
                String question = iteratorQuestion.getQuestion();
                int answerId = iteratorQuestion.getAnswerId();
                String answer = iteratorQuestion.getAnswer();
                int correctAnswer = iteratorQuestion.getCorrectAnswer();
                if (questionId < 0 || question.isEmpty()
                        || answerId < 0 || answer.isEmpty()) {
                    throw new QuestionReadException("The question file wasn't read. " +
                            "Check that the data is entered correctly", new RuntimeException());
                }
                questions.add(iteratorQuestion);
            }
        } catch (QuestionReadException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return questions;
    }
}
