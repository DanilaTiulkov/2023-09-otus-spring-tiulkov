package org.example.service;

import org.example.mapper.BrandMapper;
import org.example.model.Brand;
import org.example.model.dto.BrandDto;
import org.example.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandDto> findBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(brandMapper::getBrandDto).toList();
    }
}
