package org.example.mapper;

import org.example.model.Brand;
import org.example.model.dto.BrandDto;
import org.springframework.stereotype.Component;


@Component
public class BrandMapper {

    public BrandDto getBrandDto(Brand brand) {
        long brandId = brand.getBrandId();
        String name = brand.getName();
        String country = brand.getCountry();
        return new BrandDto(brandId, name, country);
    }
}
