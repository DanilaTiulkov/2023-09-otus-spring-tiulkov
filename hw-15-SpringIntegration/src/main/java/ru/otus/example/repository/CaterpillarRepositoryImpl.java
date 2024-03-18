package ru.otus.example.repository;

import org.springframework.stereotype.Repository;
import ru.otus.example.model.Caterpillar;
import ru.otus.example.model.enums.CaterpillarStatus;

import java.util.List;

@Repository
public class CaterpillarRepositoryImpl implements CaterpillarRepository {


    @Override
    public List<Caterpillar> getCaterpillars() {
        return List.of(new Caterpillar("red", CaterpillarStatus.ALIVE)
                    ,new Caterpillar("green", CaterpillarStatus.IN_COCOON)
                    ,new Caterpillar("blue", CaterpillarStatus.DEAD)
                    ,new Caterpillar("pink", CaterpillarStatus.ALIVE)
                    ,new Caterpillar("green", CaterpillarStatus.ALIVE)
                    ,new Caterpillar("blue", CaterpillarStatus.ALIVE)
                    ,new Caterpillar("pink", CaterpillarStatus.ALIVE));
    }
}
