package org.example.mapper;

import org.example.model.Characteristic;
import org.example.model.Brand;
import org.example.model.dto.CharacteristicDto;
import org.springframework.stereotype.Component;

@Component
public class CharacteristicMapper {

    private final BrandMapper brandMapper;

    public CharacteristicMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    public CharacteristicDto getCharacteristicDto(Characteristic characteristic) {
        long characteristicId = characteristic.getCharacteristicId();
        int batteryCapacity = characteristic.getBatteryCapacity();
        String color = characteristic.getColor();
        String size = characteristic.getSize();
        Brand brand = characteristic.getBrand();
        return new CharacteristicDto(characteristicId, batteryCapacity, color, size,
                brandMapper.getBrandDto(brand));
    }
}
