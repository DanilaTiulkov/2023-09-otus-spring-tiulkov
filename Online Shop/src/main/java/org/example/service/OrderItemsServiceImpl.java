package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mapper.OrderItemsMapper;
import org.example.model.OrderItems;
import org.example.model.dto.OrderItemsDto;
import org.example.repository.OrderItemsRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {


    private final OrderItemsRepository orderItemsRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderItemsMapper orderItemsMapper;


    public OrderItemsServiceImpl(OrderItemsRepository orderItemsRepository, OrderRepository orderRepository,
                                 ProductRepository productRepository, OrderItemsMapper orderItemsMapper) {
        this.orderItemsRepository = orderItemsRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemsMapper = orderItemsMapper;
    }

    @Override
    @Transactional
    public OrderItemsDto insert(long orderId, long productId, int productQuantity, int price) {
        var orderItems = save(0, orderId, productId, productQuantity, price);
        return orderItemsMapper.getOrderItemsDto(orderItems);
    }

    public OrderItems save(long id, long orderId, long productId, int productQuantity, int price) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        var orderItems = new OrderItems(id, order, product, productQuantity, price);
        return orderItemsRepository.save(orderItems);
    }
}
