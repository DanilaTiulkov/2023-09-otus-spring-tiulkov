package org.example.model.dto;


import java.util.Objects;

public class ProductDto {

    private long productId;

    private String title;

    private int price;

    private int quantity;

    private CategoryDto categoryDto;

    public ProductDto(long productId, String title, int price, int quantity, CategoryDto categoryDto) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categoryDto = categoryDto;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDto that = (ProductDto) o;
        return productId == that.productId && price == that.price && quantity == that.quantity
                && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, quantity);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", productId=" + productId +
                '}';
    }
}
