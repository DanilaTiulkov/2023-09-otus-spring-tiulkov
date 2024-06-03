package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(value = "product-entity-graph")
    List<Product> findAll();

    @Override
    @EntityGraph(value = "product-entity-graph")
    Optional<Product> findById(Long productId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Product p set p.quantity = quantity - :quantity where p.productId = :productId")
    void reduceQuantity(@Param("quantity") int quantity, @Param("productId") long productId);

    @Query(value = "select p from Product p inner join fetch p.category where p.title like %:productTitle%")
    List<Product> findByTitle(@Param("productTitle") String productTitle);

    @EntityGraph(value = "product-entity-graph")
    List<Product> findByCategoryCategoryId(Long categoryId);

    @Query(value = "select p " +
            "from Product p " +
            "inner join fetch p.category" +
            " where p.category.categoryId = :categoryId and p.title like %:productTitle%")
    List<Product> findByTitleAndCategoryCategoryId(@Param("categoryId")Long categoryId,
                                                   @Param("productTitle") String productTitle);
}
