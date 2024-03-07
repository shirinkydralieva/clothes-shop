package com.example.clothesshop.service;


import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.entity.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    ManufacturerDto createManufacturer(ManufacturerDto product);

    ManufacturerDto findById(Long id);

    List<ManufacturerDto> getAllManufacturers();

    void deleteById(Long id);
}
