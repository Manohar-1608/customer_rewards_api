package com.rewardpoints.rewardscalculator.rewardservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.rewardpoints.rewardscalculator.dto.RewardPointsDTO;
import com.rewardpoints.rewardscalculator.entity.Transaction;
import com.rewardpoints.rewardscalculator.exception.CustomerNotFoundException;
import com.rewardpoints.rewardscalculator.repository.TransactionRepository;
import com.rewardpoints.rewardscalculator.utils.RewardConstants;

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

    private final TransactionRepository repository;

    public RewardPointsService(TransactionRepository repository) {
	this.repository = repository;
    }

    public RewardPointsDTO calculateRewards(String customerId, LocalDate from, LocalDate to) {
	List<Transaction> customerTxns = repository.findByCustomerIdAndDateBetween(customerId, from, to);

	if (customerTxns == null || customerTxns.isEmpty()) {
	    throw new CustomerNotFoundException("No transactions found for customer ID : " + customerId);
	}

	Map<String, Integer> monthlyPoints = new HashMap<>();
	int total = RewardConstants.ZERO;
	DateTimeFormatter fmt = DateTimeFormatter.ofPattern(RewardConstants.MONTH_FORMAT);

	for (Transaction txn : customerTxns) {
	    int points = pointsCalculator.apply(txn.getAmount());
	    String month = txn.getDate().format(fmt);
	    monthlyPoints.put(month, monthlyPoints.getOrDefault(month, RewardConstants.ZERO) + points);
	    total += points;
	}

	String customerName = customerTxns.isEmpty() ? "Unknown" : customerTxns.get(0).getCustomerName();
	RewardPointsDTO rewardPointsDTO = new RewardPointsDTO(customerId, customerName, monthlyPoints, total);

	return rewardPointsDTO;
    }

    Function<Double, Integer> pointsCalculator = (amount) -> amount > RewardConstants.UPPER_LIMIT
	    ? (int) (RewardConstants.TWO_POINTS * (amount - RewardConstants.UPPER_LIMIT)) + RewardConstants.LOWER_LIMIT
	    : amount > RewardConstants.LOWER_LIMIT ? (int) (amount - RewardConstants.LOWER_LIMIT)
		    : RewardConstants.ZERO;

}
