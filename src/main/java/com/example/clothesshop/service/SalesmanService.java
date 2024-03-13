package com.example.clothesshop.service;

import com.example.clothesshop.dto.SalesmanDto;
import com.example.clothesshop.entity.Salesman;

import java.util.List;

public interface SalesmanService {
    SalesmanDto create(SalesmanDto salesman);

    SalesmanDto getById(long id);

    List<SalesmanDto> getAll();

    void deleteById(Long id);
}
