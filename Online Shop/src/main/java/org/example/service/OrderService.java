package org.example.service;

import org.example.model.dto.OrderDto;

import java.sql.Date;

public interface OrderService {

    OrderDto createOrder(String clientName, String phone, String email, String address,
                    String[] cookies, int price, Date orderDate);
}
