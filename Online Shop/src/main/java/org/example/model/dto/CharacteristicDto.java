package org.example.model.dto;

import java.util.Objects;

public class CharacteristicDto {

    private long characteristicId;

    private int batteryCapacity;

    private String color;

    private String size;

    private BrandDto brandDto;


    public CharacteristicDto(long characteristicId, int batteryCapacity, String color, String size, BrandDto brandDto) {
        this.characteristicId = characteristicId;
        this.batteryCapacity = batteryCapacity;
        this.color = color;
        this.size = size;
        this.brandDto = brandDto;
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

    public BrandDto getBrandDto() {
        return brandDto;
    }

    public void setBrandDto(BrandDto brandDto) {
        this.brandDto = brandDto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CharacteristicDto that = (CharacteristicDto) o;
        return characteristicId == that.characteristicId && batteryCapacity == that.batteryCapacity
                && Objects.equals(color, that.color) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characteristicId, batteryCapacity, color, size);
    }

    @Override
    public String toString() {
        return "CharacteristicDto{" +
                "characteristicId=" + characteristicId +
                ", batteryCapacity=" + batteryCapacity +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
