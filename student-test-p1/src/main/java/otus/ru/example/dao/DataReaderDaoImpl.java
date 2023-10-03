package otus.ru.example.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataReaderDaoImpl implements DataReaderDao {

    private final String fileName;

    public DataReaderDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<List<String>> getData() {
        List<List<String>> result = new ArrayList<>();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
                 InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(",");
                    result.add(Arrays.asList(split));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return result;
    }
}
