package com.rewardpoints.rewardscalculator.rewardservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.rewardpoints.rewardscalculator.enums.RewardPoint;
import com.rewardpoints.rewardscalculator.enums.RewardLimit;
import org.springframework.stereotype.Service;

import com.rewardpoints.rewardscalculator.dto.RewardPointsDTO;
import com.rewardpoints.rewardscalculator.entity.Transaction;
import com.rewardpoints.rewardscalculator.exception.CustomerNotFoundException;
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

    private final TransactionRepository repository;

    public static final String MONTH_FORMAT = "yyyy-MM";

    public RewardPointsService(TransactionRepository repository) {
        this.repository = repository;
    }

    public RewardPointsDTO calculateRewards(String customerId, LocalDate from, LocalDate to) {
        List<Transaction> transactions = repository.findByCustomerIdAndDateBetween(customerId, from, to);

        if (transactions == null || transactions.isEmpty()) {
            throw new CustomerNotFoundException("No transactions found for customer ID : " + customerId);
        }

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int total = RewardPoint.ZERO_POINT.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_FORMAT);

        for (Transaction transaction : transactions) {
            int points = pointsCalculator.apply(transaction.getAmount());
            String month = transaction.getDate().format(formatter);
            monthlyPoints.put(month, monthlyPoints.getOrDefault(month, RewardPoint.ZERO_POINT.getValue()) + points);
            total += points;
        }

        String customerName = transactions.isEmpty() ? "Unknown" : transactions.get(0).getCustomerName();
        return new RewardPointsDTO(customerId, customerName, monthlyPoints, total);
    }

    Function<Double, Integer> pointsCalculator = (amount) -> amount > RewardLimit.UPPER.getValue()
            ? (int) (RewardPoint.TWO_POINTS.getValue() * (amount - RewardLimit.UPPER.getValue())) + RewardLimit.LOWER.getValue()
            : amount > RewardLimit.LOWER.getValue() ? (int) (amount - RewardLimit.LOWER.getValue())
            : RewardPoint.ZERO_POINT.getValue();

}
