package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.repository.ManufacturerRepository;
import com.example.clothesshop.service.ManufacturerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    @Override
    public ManufacturerDto createManufacturer(ManufacturerDto model) {
        Manufacturer manufacturer = Manufacturer.builder()
                .name(model.getName())
                .contactInfo(model.getContactInfo())
                .address(model.getAddress())
                .build();
        try {
            manufacturerRepository.save(manufacturer);
        }catch (Exception e){
            log.error(e.getStackTrace().toString());
        }
        return model;
    }

    @Override
    public ManufacturerDto findById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Manufacturer not found with id "+ id));
        ManufacturerDto model = ManufacturerDto.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .contactInfo(manufacturer.getContactInfo())
                .address(manufacturer.getAddress())
                .build();
        return model;
    }

    @Override
    public List<ManufacturerDto> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        List<ManufacturerDto> models = new ArrayList<>();
        for (Manufacturer manufacturer : manufacturers) {
            ManufacturerDto model = ManufacturerDto.builder()
                    .id(manufacturer.getId())
                    .name(manufacturer.getName())
                    .contactInfo(manufacturer.getContactInfo())
                    .address(manufacturer.getAddress())
                    .build();
            models.add(model);
        }
        return models;
    }

    @Override
    public void deleteById(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
