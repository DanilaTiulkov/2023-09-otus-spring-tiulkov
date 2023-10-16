package otus.ru.example.dao;


import otus.ru.example.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestions();
}
