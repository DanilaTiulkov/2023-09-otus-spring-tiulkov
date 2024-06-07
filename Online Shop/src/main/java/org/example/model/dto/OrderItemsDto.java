package org.example.model.dto;

import jakarta.persistence.*;
import org.example.model.Order;
import org.example.model.Product;

import java.util.Objects;

public class OrderItemsDto {

    private long id;

    private Order order;

    private Product product;

    private int productQuantity;

    private int price;


    public OrderItemsDto(long id, Order order, Product product, int productQuantity, int price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.productQuantity = productQuantity;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
        OrderItemsDto that = (OrderItemsDto) o;
        return id == that.id && productQuantity == that.productQuantity && price == that.price
                && Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product, productQuantity, price);
    }

    @Override
    public String toString() {
        return "OrderItemsDto{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", productQuantity=" + productQuantity +
                ", price=" + price +
                '}';
    }
}
