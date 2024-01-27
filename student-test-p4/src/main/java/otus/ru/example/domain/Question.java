package otus.ru.example.domain;

import java.util.List;


public record Question (String text, List<Answer> answers) {
}
