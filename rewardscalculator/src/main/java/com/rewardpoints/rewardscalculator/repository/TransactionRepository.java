package com.rewardpoints.rewardscalculator.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardpoints.rewardscalculator.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCustomerIdAndDateBetween(String customerId, LocalDate from, LocalDate to);

}
