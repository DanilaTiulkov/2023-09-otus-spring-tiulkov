package ru.otus.example.service;

import ru.otus.example.model.Cocoon;

import java.util.List;

public interface CocoonService {

    Cocoon situation(Cocoon cocoon);

    List<Cocoon> filter(List<Cocoon> cocoons);
}
