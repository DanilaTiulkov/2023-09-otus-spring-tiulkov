package org.example.rest;

import org.example.model.dto.BrandDto;
import org.example.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/api/brands")
    public List<BrandDto> findBrands() {
        return brandService.findBrands();
    }
}
