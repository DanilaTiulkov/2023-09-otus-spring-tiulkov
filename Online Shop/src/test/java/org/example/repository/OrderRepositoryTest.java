package org.example.repository;

import org.example.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Сохранение заказа")
    public void shouldSaveOrder() {
        var expectedOrder = new Order(0, "Петр", "+7(999)999-99-99", "test@mail.ru", "Тестовый адрес", new Date(2024, 4, 2));
        var savedOrder = orderRepository.save(expectedOrder);

        assertThat(savedOrder)
                .isNotNull()
                .matches(order -> order.getOrderId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedOrder);

        assertThat(em.find(Order.class, savedOrder.getOrderId()))
                .isNotNull()
                .isEqualTo(savedOrder);
    }
}
