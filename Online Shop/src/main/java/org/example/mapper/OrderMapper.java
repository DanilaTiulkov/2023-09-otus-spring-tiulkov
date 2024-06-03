package org.example.mapper;

import org.example.model.Order;
import org.example.model.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDto getOrderDto(Order order) {
        var orderId = order.getOrderId();
        var clientName = order.getClientName();
        var phone = order.getPhone();
        var email = order.getEmail();
        var address = order.getAddress();
        var orderDate = order.getOrderDate();
        return new OrderDto(orderId, clientName, phone, email, address, orderDate);
    }
}
