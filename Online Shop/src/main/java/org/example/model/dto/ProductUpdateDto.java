package org.example.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ProductUpdateDto {

    private long productId;

    @NotEmpty
    private String title;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    @Min(1)
    private Long categoryId;

    public ProductUpdateDto(long productId, String title, Integer price, Integer quantity, Long categoryId) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
        ProductUpdateDto that = (ProductUpdateDto) o;
        return productId == that.productId && Objects.equals(title, that.title)
                && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity)
                && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, quantity, categoryId);
    }

    @Override
    public String toString() {
        return "ProductUpdateDto{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categoryId=" + categoryId +
                '}';
    }
}
