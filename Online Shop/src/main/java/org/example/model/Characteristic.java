package org.example.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@NamedEntityGraph(name = "characteristic-entity-graph",
        attributeNodes = {@NamedAttributeNode("brand")})
@Table(name = "Characteristics")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long characteristicId;

    @Column
    private int batteryCapacity;

    @Column
    private String color;

    @Column
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public Characteristic(long characteristicId, int batteryCapacity, String color,
                          String size, Brand brand, Product product) {
        this.characteristicId = characteristicId;
        this.batteryCapacity = batteryCapacity;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.product = product;
    }

    public Characteristic() {
    }

    public long getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(long characteristicId) {
        this.characteristicId = characteristicId;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Characteristic that = (Characteristic) o;
        return characteristicId == that.characteristicId && batteryCapacity == that.batteryCapacity
                && Objects.equals(color, that.color) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characteristicId, batteryCapacity, color, size);
    }

    @Override
    public String toString() {
        return "Characteristic{" +
                "characteristicId=" + characteristicId +
                ", batteryCapacity=" + batteryCapacity +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
