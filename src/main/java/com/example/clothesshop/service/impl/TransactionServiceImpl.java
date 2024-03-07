package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.TransactionDto;
import com.example.clothesshop.entity.Buyer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.entity.Transaction;
import com.example.clothesshop.exception.NotFoundException;
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
    private final ProductRepository productRepository;
    private final ProductService productService;
    @Override
    public TransactionDto sellProduct(TransactionDto model) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(model.getSalesmanId()).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + model.getSalesmanId()));
        Buyer buyer = buyerRepository.findById(model.getBuyerId()).orElseThrow(()-> new NotFoundException("Buyer not found with id:" + model.getBuyerId()));
        Product product = productRepository.findById(model.getProductId()).orElseThrow(()-> new NotFoundException("Product not found with id:" + model.getProductId()));
        if (product.getQuantity() < model.getQuantity()){
            throw new IllegalArgumentException("Insufficient stock quantity");
        } else {
            Transaction transaction = Transaction.builder()
                    .salesman(salesman)
                    .buyer(buyer)
                    .product(product)
                    .quantity(model.getQuantity())
                    .build();
                transactionRepository.save(transaction);
                productService.decreaseStockQuantity(product, model.getQuantity());
                return model;
        }
    }

    @Override
    public List<TransactionDto> getAll() {
       List<Transaction> transactions = transactionRepository.findAll();
        return getTransactionDtos(transactions);
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Transaction not found with id:" + id));
        TransactionDto model = TransactionDto.builder()
                .id(transaction.getId())
                .salesmanId(transaction.getSalesman().getId())
                .productId(transaction.getProduct().getId())
                .buyerId(transaction.getBuyer().getId())
                .quantity(transaction.getQuantity())
                .build();
        return model;
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
        return getTransactionDtos(transactions);
    }

    @Override
    public List<TransactionDto> getAllBySalesmanId(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()->new NotFoundException("Salesman not found with id: " + salesmanId));
        List<Transaction> transactions = transactionRepository.getAllBySalesmanId(salesmanId);
        return getTransactionDtos(transactions);
    }

    private List<TransactionDto> getTransactionDtos(List<Transaction> transactions) {
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
