package com.example.clothesshop.service;

import com.example.clothesshop.dto.BuyerDto;
import com.example.clothesshop.dto.TransactionDto;
import com.example.clothesshop.exception.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BuyerService {
    BuyerDto create(BuyerDto model);
    List<BuyerDto> getAll();
    BuyerDto getById(Long id) throws NotFoundException;
    BuyerDto update(Long id, BuyerDto model) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
}
