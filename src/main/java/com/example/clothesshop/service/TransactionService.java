package com.example.clothesshop.service;

import com.example.clothesshop.dto.TransactionDto;
import com.example.clothesshop.entity.Transaction;
import com.example.clothesshop.exception.NotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionService {
    TransactionDto sellProduct(TransactionDto model) throws NotFoundException;
    List<TransactionDto> getAll();
    TransactionDto getById(Long id);
    TransactionDto update(Long id, TransactionDto model);
    void delete(Long id);
    List<TransactionDto> getAllByBuyerId(Long buyerId) throws NotFoundException;
    List<TransactionDto> getAllBySalesmanId(Long salesmanId) throws NotFoundException;
}
