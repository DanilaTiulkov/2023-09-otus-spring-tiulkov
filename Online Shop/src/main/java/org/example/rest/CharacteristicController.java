package org.example.rest;

import jakarta.validation.Valid;
import org.example.model.dto.CharacteristicDto;
import org.example.model.dto.CharacteristicNewDto;
import org.example.model.dto.CharacteristicUpdateDto;
import org.example.service.CharacteristicService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CharacteristicController {

    private final CharacteristicService characteristicService;

    public CharacteristicController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping("/api/products/{id}/characteristics")
    public CharacteristicDto findCharacteristicByProductId(@PathVariable long id) {
        return characteristicService.findByProductId(id);
    }

    @PostMapping("/api/characteristics")
    public CharacteristicDto createCharacteristic(@Valid @RequestBody CharacteristicNewDto characteristicNewDto) {
        int batteryCapacity = characteristicNewDto.getBatteryCapacity();
        String color = characteristicNewDto.getColor();
        String size = characteristicNewDto.getSize();
        long brandId = characteristicNewDto.getBrandId();
        long productId = characteristicNewDto.getProductId();
        return characteristicService.insert(batteryCapacity, color, size, brandId, productId);
    }

    @PutMapping("/api/characteristics/{id}")
    public CharacteristicDto updateCharacteristic(@Valid
                                                      @RequestBody CharacteristicUpdateDto characteristicUpdateDtoDto) {
        long characteristicId = characteristicUpdateDtoDto.getCharacteristicId();
        int batteryCapacity = characteristicUpdateDtoDto.getBatteryCapacity();
        String color = characteristicUpdateDtoDto.getColor();
        String size = characteristicUpdateDtoDto.getSize();
        long brandId = characteristicUpdateDtoDto.getBrandId();
        long productId = characteristicUpdateDtoDto.getProductId();
        return characteristicService.update(characteristicId, batteryCapacity, color, size, brandId, productId);
    }
}

