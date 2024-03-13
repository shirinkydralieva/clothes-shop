package com.example.clothesshop.service.impl;

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
import com.example.clothesshop.repository.TransactionRepository;
import com.example.clothesshop.service.ProductService;
import com.example.clothesshop.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final SalesmanRepository salesmanRepository;
    private final BuyerRepository buyerRepository;
    private final ProductService productService;
    private final TransactionMapper transactionMapper;
    @Override
    public TransactionDto sellProduct(TransactionDto transactionDto) throws NotFoundException {
        if (new Product(transactionDto.getProductId()).getQuantity() < transactionDto.getQuantity()){
            throw new IllegalArgumentException("Insufficient stock quantity");
        } else {
            Transaction transaction = transactionMapper.toEntity(transactionDto);
                transactionRepository.save(transaction);
                productService.decreaseStockQuantity(transaction.getProduct(), transactionDto.getQuantity());
                return transactionDto;
        }
    }

    @Override
    public List<TransactionDto> getAll() {
       List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.toDtoList(transactions);
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Transaction not found with id:" + id));
        return transactionMapper.toDto(transaction);
    }

    @Override
    public TransactionDto update(Long id, TransactionDto model) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Transaction not found with id:" + id));
       if (model.getQuantity() != null){
           transaction.setQuantity(model.getQuantity());
       }
       return model;
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Transaction not found with id:" + id));
        transactionRepository.delete(transaction);
    }

    @Override
    public List<TransactionDto> getAllByBuyerId(Long buyerId) throws NotFoundException {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(()->new NotFoundException("Buyer not found with id: " + buyerId));
        List<Transaction> transactions = transactionRepository.getAllByBuyerId(buyerId);
        return transactionMapper.toDtoList(transactions);
    }

    @Override
    public List<TransactionDto> getAllBySalesmanId(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()->new NotFoundException("Salesman not found with id: " + salesmanId));
        List<Transaction> transactions = transactionRepository.getAllBySalesmanId(salesmanId);
        return transactionMapper.toDtoList(transactions);
    }

}
