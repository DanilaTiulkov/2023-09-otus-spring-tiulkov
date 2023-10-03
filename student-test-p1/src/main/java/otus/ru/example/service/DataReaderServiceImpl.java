package otus.ru.example.service;

import otus.ru.example.dao.DataReaderDao;


import java.util.List;

public class DataReaderServiceImpl implements DataReaderService {

    private final DataReaderDao reader;

    public DataReaderServiceImpl(DataReaderDao reader) {
        this.reader = reader;
    }

    @Override
    public void print() {
        List<List<String>> result = reader.getData();
        result.forEach(list -> System.out.println(list.toString()
                .replace("[", "")
                .replace("]","")
                .replace(",", "")));
    }
}
