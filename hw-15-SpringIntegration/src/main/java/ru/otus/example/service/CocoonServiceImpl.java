package ru.otus.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.example.model.Cocoon;
import ru.otus.example.model.enums.CocoonStatus;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class CocoonServiceImpl implements CocoonService {

    private final CocoonStatus[] cocoonStatus = new CocoonStatus[]{CocoonStatus.DESTROYED, CocoonStatus.FILLED};

    @Override
    public Cocoon situation(Cocoon cocoon) {
        log.info("Status of cocoon before a situation is {}", cocoon.getStatus());
        cocoon.setStatus(getRandomStatus());
        log.info("Status of cocoon after a situation is {}", cocoon.getStatus());
        return cocoon;
    }

    @Override
    public List<Cocoon> filter(List<Cocoon> cocoons) {
        return cocoons.stream().filter(cocoon ->
                cocoon.getStatus().equals(CocoonStatus.FILLED)).toList();
    }

    private CocoonStatus getRandomStatus() {
        Random random = new Random();
        return cocoonStatus[random.nextInt(cocoonStatus.length)];
    }
}
