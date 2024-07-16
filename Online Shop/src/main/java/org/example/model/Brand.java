package org.example.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long brandId;

    @Column
    private String name;

    @Column
    private String country;

    public Brand(long brandId, String name, String country) {
        this.brandId = brandId;
        this.name = name;
        this.country = country;
    }

    public Brand() {
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
        Brand brand = (Brand) o;
        return brandId == brand.brandId && Objects.equals(name, brand.name) && Objects.equals(country, brand.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, name, country);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
