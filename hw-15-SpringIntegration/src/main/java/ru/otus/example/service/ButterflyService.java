package ru.otus.example.service;

import ru.otus.example.model.Butterfly;
import ru.otus.example.model.Cocoon;

import java.util.List;

public interface ButterflyService {

    List<Butterfly> transform(List<Cocoon> cocoons);
}
