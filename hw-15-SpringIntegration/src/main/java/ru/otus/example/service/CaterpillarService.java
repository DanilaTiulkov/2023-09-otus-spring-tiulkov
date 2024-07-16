package ru.otus.example.service;

import ru.otus.example.model.Butterfly;
import ru.otus.example.model.Caterpillar;

import java.util.List;

public interface CaterpillarService {

    List<Butterfly> start();

    Caterpillar checkStatus(Caterpillar caterpillar);

    List<Caterpillar> getCaterpillars();

    List<Caterpillar> filter(List<Caterpillar> caterpillars);
}
