package otus.ru.example.dao;

import otus.ru.example.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    public QuestionDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> result = new ArrayList<>();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
                 InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] split = line.split(",");
                    int questionId = Integer.parseInt(split[0]);
                    String question = split[1];
                    String answer = split[2];
                    int correctAnswer = Integer.parseInt(split[3]);
                    result.add(new Question(questionId, question, answer, correctAnswer));

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return result;
    }
}
