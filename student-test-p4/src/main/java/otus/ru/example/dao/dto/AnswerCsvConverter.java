package otus.ru.example.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import otus.ru.example.domain.Answer;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) {
        var valueArr = value.split("%");
        return new Answer(valueArr[0], Boolean.parseBoolean(valueArr[1]));

    }
}
