package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.mapper.ManufacturerMapper;
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
    private final ManufacturerMapper manufacturerMapper;
    @Override
    public ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDto);
       return manufacturerMapper.toDto(manufacturerRepository.save(manufacturer));
    }

    @Override
    public ManufacturerDto findById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Manufacturer not found with id "+ id));
        return manufacturerMapper.toDto(manufacturer);
    }

    @Override
    public List<ManufacturerDto> getAllManufacturers() {
        return manufacturerMapper.toDtoList(manufacturerRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
