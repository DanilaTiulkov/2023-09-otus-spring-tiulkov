package ru.otus.example.repository;


import ru.otus.example.model.h2.temp.AuthorTemp;

import java.util.List;

public interface AuthorTempRepository {

    List<AuthorTemp> findAll();
}
