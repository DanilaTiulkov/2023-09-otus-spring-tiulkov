package org.example.service;

import org.example.model.dto.OrderItemsDto;

public interface OrderItemsService {

    public OrderItemsDto insert(long orderId, long productId, int productQuantity, int price);
}
