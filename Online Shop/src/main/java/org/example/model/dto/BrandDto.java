package org.example.model.dto;


import java.util.Objects;

public class BrandDto {

    private long brandId;

    private String name;

    private String country;

    public BrandDto(long brandId, String name, String country) {
        this.brandId = brandId;
        this.name = name;
        this.country = country;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BrandDto brandDto = (BrandDto) o;
        return brandId == brandDto.brandId && Objects.equals(name, brandDto.name)
                && Objects.equals(country, brandDto.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, name, country);
    }

    @Override
    public String toString() {
        return "BrandDto{" +
                "brandId=" + brandId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
