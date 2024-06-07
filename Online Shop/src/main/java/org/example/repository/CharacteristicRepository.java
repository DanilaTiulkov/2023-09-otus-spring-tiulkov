package org.example.repository;

import org.example.model.Characteristic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    @EntityGraph(value = "characteristic-entity-graph")
    public Characteristic findByProductProductId(long productId);
}
