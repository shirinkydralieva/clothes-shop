package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.dto.SalesmanDto;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.mapper.SalesmanMapper;
import com.example.clothesshop.repository.SalesmanRepository;
import com.example.clothesshop.service.SalesmanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesmanServiceImpl implements SalesmanService {
    private final SalesmanRepository salesmanRepository;
    private final SalesmanMapper salesmanMapper;
    @Override
    public SalesmanDto create(SalesmanDto model) {
        Salesman salesman = salesmanMapper.toEntity(model);
        return salesmanMapper.toDto(salesmanRepository.save(salesman));

    }

    @Override
    public SalesmanDto getById(long id) {
        Salesman salesman = salesmanRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Salesman not found with id"+ id));
        return salesmanMapper.toDto(salesman);
    }

    @Override
    public List<SalesmanDto> getAll() {
        return salesmanMapper.toDtoList(salesmanRepository.findAll());
    }
    @Override
    public void deleteById(Long id) {
        salesmanRepository.deleteById(id);
    }
}
