package com.rewardpoints.rewardscalculator.rewardservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardpoints.rewardscalculator.dto.RewardPointsDTO;
import com.rewardpoints.rewardscalculator.entity.Transaction;
import com.rewardpoints.rewardscalculator.repository.TransactionRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RewardPointsService {

    @PostConstruct
    public void initData() {
	repository.save(new Transaction("C1", "Mohan", LocalDate.of(2025, 5, 10), 120));
	repository.save(new Transaction("C1", "Mohan", LocalDate.of(2025, 6, 15), 75));
	repository.save(new Transaction("C1", "Mohan", LocalDate.of(2025, 7, 5), 40));
	repository.save(new Transaction("C2", "Anant", LocalDate.of(2025, 5, 12), 200));
	repository.save(new Transaction("C2", "Anant", LocalDate.of(2025, 6, 18), 90));
    }

    @Autowired
    private TransactionRepository repository;

    public RewardPointsDTO calculateRewards(String customerId, LocalDate from, LocalDate to) {
	List<Transaction> customerTxns = repository.findByCustomerIdAndDateBetween(customerId, from, to);

	Map<String, Integer> monthlyPoints = new HashMap<>();
	int total = 0;
	DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

	for (Transaction txn : customerTxns) {
	    int points = calculatePoints(txn.getAmount());
	    String month = txn.getDate().format(fmt);
	    monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
	    total += points;
	}

	String customerName = customerTxns.isEmpty() ? "Unknown" : customerTxns.get(0).getCustomerName();
	RewardPointsDTO rewardPointsDTO = new RewardPointsDTO(customerId, customerName, monthlyPoints, total);

	return rewardPointsDTO;
    }

    private int calculatePoints(double amount) {
	int points = 0;
	if (amount > 100)
	    points += (int) (2 * (amount - 100)) + 50;
	else if (amount > 50)
	    points += (int) (amount - 50);
	return points;
    }

}
