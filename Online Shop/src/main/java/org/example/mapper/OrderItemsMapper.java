package org.example.mapper;

import org.example.model.OrderItems;
import org.example.model.dto.OrderItemsDto;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {

    public OrderItemsDto getOrderItemsDto(OrderItems orderItems) {
        var id = orderItems.getId();
        var order = orderItems.getOrder();
        var product = orderItems.getProduct();
        var quantity = orderItems.getProductQuantity();
        var price = orderItems.getPrice();
        return new OrderItemsDto(id, order, product, quantity, price);
    }
}
