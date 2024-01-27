package otus.ru.example.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import otus.ru.example.dao.dto.QuestionDto;
import otus.ru.example.domain.Answer;
import otus.ru.example.exceptions.QuestionReadException;
import otus.ru.example.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvQuestionDao implements QuestionDao {

    private final String fileName;

    public QuestionDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestions() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(br)
                    .withType(QuestionDto.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .withIgnoreEmptyLine(true)
                    .build();
            Iterator<QuestionDto> iterator = csvToBean.iterator();
            List<Question> questions = questionsDtoToQuestions(iterator);
            return questions;
        } catch (IOException ex) {
            throw new QuestionReadException(ex.getMessage(), ex);
        }
    }

    private List<Question> questionsDtoToQuestions(Iterator<QuestionDto> iterator) {
        List<Question> questions = new ArrayList<>();
        try {
            while (iterator.hasNext()) {
                Question iteratorQuestion = iterator.next().toDomainObject();
                checkQuestion(iteratorQuestion);
                questions.add(iteratorQuestion);
            }
        } catch (QuestionReadException ex) {
            throw new QuestionReadException(ex.getMessage());
        }
        return questions;
    }

    private void checkQuestion (Question question) throws QuestionReadException {
        String questionText = question.text();
        List<Answer> answers = question.answers();
        if (questionText.isEmpty()) {
            throw new QuestionReadException("The question file wasn't read. " +
                    "The question field is empty");
        }
        answers.forEach(answer -> {
            if (answer.text().isEmpty()) {
                throw new QuestionReadException("The question file wasn't read. " +
                        "the answer field is empty");
            }
        });
    }
}
