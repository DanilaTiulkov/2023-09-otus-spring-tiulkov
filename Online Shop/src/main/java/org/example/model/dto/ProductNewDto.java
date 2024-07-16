package org.example.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ProductNewDto {

    @NotEmpty
    private String title;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    @Min(1)
    private Long categoryId;


    public ProductNewDto(String title, Integer price, Integer quantity, Long categoryId) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductNewDto that = (ProductNewDto) o;
        return Objects.equals(title, that.title) && Objects.equals(price, that.price)
                && Objects.equals(quantity, that.quantity) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, quantity, categoryId);
    }

    @Override
    public String toString() {
        return "ProductNewDto{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categoryId=" + categoryId +
                '}';
    }
}
