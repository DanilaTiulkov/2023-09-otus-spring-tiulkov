package org.example.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CharacteristicNewDto {

    @NotNull
    private Integer batteryCapacity;

    @NotEmpty
    private String color;

    @NotEmpty
    private String size;

    @Min(1)
    private Long brandId;

    @NotNull
    private Long productId;

    public CharacteristicNewDto(Integer batteryCapacity, String color, String size, Long brandId, Long productId) {
        this.batteryCapacity = batteryCapacity;
        this.color = color;
        this.size = size;
        this.brandId = brandId;
        this.productId = productId;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CharacteristicNewDto that = (CharacteristicNewDto) o;
        return batteryCapacity == that.batteryCapacity && Objects.equals(color, that.color)
                && Objects.equals(size, that.size) && Objects.equals(brandId, that.brandId)
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batteryCapacity, color, size, brandId, productId);
    }

    @Override
    public String toString() {
        return "CharacteristicNewDto{" +
                "batteryCapacity=" + batteryCapacity +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", brandId=" + brandId +
                ", productId=" + productId +
                '}';
    }
}
