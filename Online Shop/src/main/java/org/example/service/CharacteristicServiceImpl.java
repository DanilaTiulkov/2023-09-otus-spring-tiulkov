package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mapper.CharacteristicMapper;
import org.example.model.Brand;
import org.example.model.Characteristic;
import org.example.model.Product;
import org.example.model.dto.CharacteristicDto;
import org.example.repository.BrandRepository;
import org.example.repository.CharacteristicRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacteristicServiceImpl implements CharacteristicService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CharacteristicRepository characteristicRepository;

    private final CharacteristicMapper characteristicMapper;


    @Autowired
    public CharacteristicServiceImpl(ProductRepository productRepository, BrandRepository brandRepository,
                                     CharacteristicRepository characteristicRepository,
                                     CharacteristicMapper characteristicMapper) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.characteristicRepository = characteristicRepository;
        this.characteristicMapper = characteristicMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public CharacteristicDto findByProductId(long productId) {
        Characteristic characteristic = characteristicRepository.findByProductProductId(productId);
        return characteristicMapper.getCharacteristicDto(characteristic);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CharacteristicDto insert(int batteryCapacity, String color, String size, long brandId, long productId) {
        Characteristic characteristic = save(0, batteryCapacity, color, size, brandId, productId);
        return characteristicMapper.getCharacteristicDto(characteristic);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CharacteristicDto update(long characteristicId, int batteryCapacity, String color, String size,
                                    long brandId, long productId) {
        Characteristic characteristic = save(characteristicId, batteryCapacity, color, size, brandId, productId);
        return characteristicMapper.getCharacteristicDto(characteristic);
    }


    public Characteristic save(long characteristicId, int batteryCapacity, String color,
                               String size, long brandId, long productId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Characteristic characteristic = new Characteristic(characteristicId, batteryCapacity, color,
                                                            size, brand, product);
        return characteristicRepository.save(characteristic);
    }
}
