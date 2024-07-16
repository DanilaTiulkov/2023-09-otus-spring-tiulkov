package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.Butterfly;
import ru.otus.example.model.Cocoon;
import ru.otus.example.model.enums.ButterflyStatus;

import java.util.List;

@Service
public class ButterflyServiceImpl implements ButterflyService {

    @Override
    public List<Butterfly> transform(List<Cocoon> cocoons) {
        return cocoons.stream().map(cocoon ->
                new Butterfly(cocoon.getColor(), ButterflyStatus.ALIVE)).toList();
    }
}
