package org.example.rest;

import jakarta.validation.Valid;
import org.example.model.dto.OrderDto;
import org.example.model.dto.OrderNewDto;
import org.example.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public OrderDto createOrder(@Valid @RequestBody OrderNewDto orderNewDto) {
        var clientName = orderNewDto.getClientName();
        var phone = orderNewDto.getPhone();
        var email = orderNewDto.getEmail();
        var address = orderNewDto.getAddress();
        var cookies = orderNewDto.getCookies();
        var price = orderNewDto.getPrice();
        Date orderDate = orderNewDto.getOrderDate();
        return orderService.createOrder(clientName, phone, email, address, cookies, price, orderDate);
    }
}
