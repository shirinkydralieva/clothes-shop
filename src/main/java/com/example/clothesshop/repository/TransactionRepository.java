package com.example.clothesshop.repository;

import com.example.clothesshop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getAllByBuyerId(Long buyerId);

    @Query("SELECT t from Transaction t where t.salesman.id = :salesmanId")
    List<Transaction> getAllBySalesmanId(@Param("salesmanId") Long salesmanId);
}
