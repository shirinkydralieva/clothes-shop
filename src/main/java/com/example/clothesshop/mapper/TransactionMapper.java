package com.example.clothesshop.mapper;

import com.example.clothesshop.dto.TransactionDto;
import com.example.clothesshop.entity.Transaction;
import com.example.clothesshop.exception.NotFoundException;

import java.util.List;

public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto) throws NotFoundException;
    List<TransactionDto> toDtoList(List<Transaction> transactions);
}
