package com.example.clothesshop.mapper.impl;

import com.example.clothesshop.dto.TransactionDto;
import com.example.clothesshop.entity.Buyer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.entity.Transaction;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.mapper.TransactionMapper;
import com.example.clothesshop.repository.BuyerRepository;
import com.example.clothesshop.repository.ProductRepository;
import com.example.clothesshop.repository.SalesmanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {
    private final SalesmanRepository salesmanRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    @Override
    public TransactionDto toDto(Transaction transaction) {
       return TransactionDto.builder()
               .id(transaction.getId())
               .salesmanId(transaction.getSalesman().getId())
               .productId(transaction.getProduct().getId())
               .buyerId(transaction.getBuyer().getId())
               .quantity(transaction.getQuantity())
               .build();
    }

    @Override
    public Transaction toEntity(TransactionDto transactionDto) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(transactionDto.getSalesmanId()).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + transactionDto.getSalesmanId()));
        Buyer buyer = buyerRepository.findById(transactionDto.getBuyerId()).orElseThrow(()-> new NotFoundException("Buyer not found with id:" + transactionDto.getBuyerId()));
        Product product = productRepository.findById(transactionDto.getProductId()).orElseThrow(()-> new NotFoundException("Product not found with id:" + transactionDto.getProductId()));
        Transaction transaction = Transaction.builder()
                .salesman(salesman)
                .buyer(buyer)
                .product(product)
                .quantity(transactionDto.getQuantity())
                .build();
        return transaction;
    }

    @Override
    public List<TransactionDto> toDtoList(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions){
            TransactionDto transactionDto = TransactionDto.builder()
                    .id(transaction.getId())
                    .salesmanId(transaction.getSalesman().getId())
                    .productId(transaction.getProduct().getId())
                    .buyerId(transaction.getBuyer().getId())
                    .quantity(transaction.getQuantity())
                    .build();
            transactionDtos.add(transactionDto);
        }
        return transactionDtos;
    }
}
