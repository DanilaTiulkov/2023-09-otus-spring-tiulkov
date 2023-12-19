package otus.ru.example.dao.dto;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import otus.ru.example.domain.Answer;
import otus.ru.example.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDto {

    @CsvBindByPosition(position = 0)
    private String text;

    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = Answer.class,
            converter = AnswerCsvConverter.class, splitOn = "\\|")
    private List<Answer> answers;

    public Question toDomainObject() {
        return new Question(text, answers);
    }
}
