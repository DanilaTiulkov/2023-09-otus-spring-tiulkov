package org.example.service;

import org.example.mapper.OrderMapper;
import org.example.model.Order;
import org.example.model.dto.OrderDto;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final OrderItemsService orderItemsService;

    private final OrderMapper orderMapper;


    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService,
                            OrderItemsService orderItemsService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemsService = orderItemsService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDto createOrder(String clientName, String phone, String email, String address,
                           String[] cookies, int price, Date orderDate) {
        var order = save(0, clientName, phone, email, address, orderDate);
        var orderId = order.getOrderId();
        for (int i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].split("=");
            var productId = Long.parseLong(cookie[0]);
            var productQuantity = Integer.parseInt(cookie[1]);
            productService.reduceQuantity(productQuantity, productId);
            orderItemsService.insert(orderId, productId, productQuantity, price);
        }
        return orderMapper.getOrderDto(order);
    }

    public Order save(long id, String clientName, String phone, String email, String address, Date orderDate) {
        var order = new Order(id, clientName, phone, email, address, orderDate);
        return orderRepository.save(order);
    }
}
