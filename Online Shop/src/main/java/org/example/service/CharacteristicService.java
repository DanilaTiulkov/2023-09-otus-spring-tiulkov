package org.example.service;

import org.example.model.dto.CharacteristicDto;

public interface CharacteristicService {

    public CharacteristicDto findByProductId(long productId);

    public CharacteristicDto insert(int batteryCapacity, String color, String size, long brandId, long productId);

    public CharacteristicDto update(long characteristicId, int batteryCapacity, String color,
                                    String size, long brandId, long productId);
}
