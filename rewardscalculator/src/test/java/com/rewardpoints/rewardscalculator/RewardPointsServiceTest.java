package com.rewardpoints.rewardscalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rewardpoints.rewardscalculator.dto.RewardPointsDTO;
import com.rewardpoints.rewardscalculator.entity.Transaction;
import com.rewardpoints.rewardscalculator.repository.TransactionRepository;
import com.rewardpoints.rewardscalculator.rewardservice.RewardPointsService;

public class RewardPointsServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private RewardPointsService rewardService;

    @BeforeEach
    public void setup() {
	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateRewards_MultipleMonths() {
	List<Transaction> transactions = List.of(new Transaction("C1", "Amit", LocalDate.of(2025, 5, 10), 120),
		new Transaction("C1", "Amit", LocalDate.of(2025, 6, 15), 75),
		new Transaction("C1", "Amit", LocalDate.of(2025, 7, 5), 40));

	when(repository.findByCustomerIdAndDateBetween(eq("C1"), any(), any())).thenReturn(transactions);

	RewardPointsDTO response = rewardService.calculateRewards("C1", LocalDate.of(2025, 5, 1),
		LocalDate.of(2025, 7, 31));

	assertEquals("C1", response.getCustomerId());
	assertEquals("Alice", response.getCustomerName());
	assertEquals(3, response.getMonthlyPoints().size());
	assertEquals(90, response.getTotalPoints());
    }

    @Test
    public void testCalculateRewards_NoTransactions() {
	when(repository.findByCustomerIdAndDateBetween(eq("C3"), any(), any())).thenReturn(List.of());

	RewardPointsDTO response = rewardService.calculateRewards("C3", LocalDate.of(2025, 5, 1),
		LocalDate.of(2025, 7, 31));

	assertEquals("C3", response.getCustomerId());
	assertEquals("Unknown", response.getCustomerName());
	assertTrue(response.getMonthlyPoints().isEmpty());
	assertEquals(0, response.getTotalPoints());
    }

}
