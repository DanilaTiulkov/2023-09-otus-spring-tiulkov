package otus.ru.example.dao;


import otus.ru.example.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestions();
}
