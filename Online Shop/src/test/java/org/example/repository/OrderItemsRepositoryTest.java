package org.example.repository;

import org.example.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderItemsRepositoryTest {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("Сохранение деталей заказа")
    public void shouldSaveOrderItems() {
        var order = new Order(0, "Петр", "+7(999)999-99-99", "test@mail.ru", "Тестовый адрес", new Date(2024, 4, 2));
        em.persist(order);
        var product = em.find(Product.class, 1L);
        var expectedOrderItems = new OrderItems(0, order, product, 1, 18000);
        var savedOrderItems = orderItemsRepository.save(expectedOrderItems);

        assertThat(savedOrderItems)
                .isNotNull()
                .matches(orderItems -> orderItems.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedOrderItems);

        assertThat(em.find(OrderItems.class, savedOrderItems.getId()))
                .isNotNull()
                .isEqualTo(savedOrderItems);
    }

}
