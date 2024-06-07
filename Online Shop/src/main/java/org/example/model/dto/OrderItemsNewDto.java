package org.example.model.dto;


import jakarta.validation.constraints.Min;

import java.util.Objects;

public class OrderItemsNewDto {

    @Min(1)
    private Long orderId;

    @Min(1)
    private Long productId;

    @Min(1)
    private Integer productQuantity;

    @Min(1)
    private Integer price;


    public OrderItemsNewDto(Long orderId, Long productId, Integer productQuantity, Integer price) {
        this.orderId = orderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItemsNewDto that = (OrderItemsNewDto) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId)
                && Objects.equals(productQuantity, that.productQuantity) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, productQuantity, price);
    }

    @Override
    public String toString() {
        return "OrderItemsNewDto{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", price=" + price +
                '}';
    }
}
