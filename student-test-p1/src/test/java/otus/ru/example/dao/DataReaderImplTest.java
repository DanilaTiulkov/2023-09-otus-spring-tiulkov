package otus.ru.example.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderImplTest {
    @DisplayName("Проверка размера коллекций внутри коллекции result")
    @Test
    void getDataTest() {
        DataReaderDaoImpl dri = new DataReaderDaoImpl("Questions.csv");
        List<List<String>> result = dri.getData();
        for (List<String> collection : result) {
            assertEquals(3, collection.size());
        }

    }
}