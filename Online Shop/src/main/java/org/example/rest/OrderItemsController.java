package org.example.rest;

import jakarta.validation.Valid;
import org.example.model.dto.OrderItemsDto;
import org.example.model.dto.OrderItemsNewDto;
import org.example.service.OrderItemsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemsController {

    private final OrderItemsService orderItemsService;


    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @PostMapping("/api/order-items")
    public OrderItemsDto createOrderItems(@Valid @RequestBody OrderItemsNewDto orderItemsNewDto) {
        var orderId = orderItemsNewDto.getOrderId();
        var productId = orderItemsNewDto.getProductId();
        var productQuantity = orderItemsNewDto.getProductQuantity();
        var price = orderItemsNewDto.getPrice();
        return orderItemsService.insert(orderId, productId, productQuantity, price);
    }
}
