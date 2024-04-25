package ru.otus.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.config.InsectsGateway;
import ru.otus.example.model.Butterfly;
import ru.otus.example.model.Caterpillar;
import ru.otus.example.model.enums.CaterpillarStatus;
import ru.otus.example.repository.CaterpillarRepository;

import java.util.List;

@Service
@Slf4j
public class CaterpillarServiceImpl implements CaterpillarService {

    private final CaterpillarRepository caterpillarRepository;

    private final InsectsGateway gateway;

    @Autowired
    public CaterpillarServiceImpl(CaterpillarRepository caterpillarRepository, InsectsGateway gateway) {
        this.caterpillarRepository = caterpillarRepository;
        this.gateway = gateway;
    }

    @Override
    public List<Butterfly> start() {
        List<Butterfly> butterflies = gateway.process(getCaterpillars());
        butterflies.forEach(butterfly ->
                log.info("Butterfly status is {}, color {}", butterfly.getStatus(), butterfly.getColor()));
        return butterflies;
    }

    @Override
    public Caterpillar checkStatus(Caterpillar caterpillar) {
        log.info("Status of caterpillar is {}", caterpillar.getStatus());
        return caterpillar;
    }

    @Override
    public List<Caterpillar> getCaterpillars() {
        return caterpillarRepository.getCaterpillars();
    }

    @Override
    public List<Caterpillar> filter(List<Caterpillar> caterpillars) {
        return caterpillars.stream().filter(caterpillar ->
                caterpillar.getStatus().equals(CaterpillarStatus.ALIVE)).toList();
    }


}
