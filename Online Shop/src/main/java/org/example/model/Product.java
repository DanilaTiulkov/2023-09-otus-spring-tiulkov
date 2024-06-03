package org.example.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@NamedEntityGraph(name = "product-entity-graph",
        attributeNodes = {@NamedAttributeNode("category")})
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column
    private String title;

    @Column
    private int price;

    @Column
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(long productId, String title, int price, int quantity, Category category) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Product() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return productId == product.productId && price == product.price && quantity == product.quantity
                && Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", productId=" + productId +
                '}';
    }
}
