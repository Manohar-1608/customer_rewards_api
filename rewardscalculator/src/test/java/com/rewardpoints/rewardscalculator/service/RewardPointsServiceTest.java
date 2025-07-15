package com.rewardpoints.rewardscalculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rewardpoints.rewardscalculator.dto.RewardPointsDTO;
import com.rewardpoints.rewardscalculator.entity.Transaction;
import com.rewardpoints.rewardscalculator.exception.CustomerNotFoundException;
import com.rewardpoints.rewardscalculator.repository.TransactionRepository;
import com.rewardpoints.rewardscalculator.rewardservice.RewardPointsService;

public class RewardPointsServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private RewardPointsService rewardService;

    private Function<Double, Integer> pointsCalculator;

    @BeforeEach
    public void setup() throws Exception {
	MockitoAnnotations.openMocks(this);
	RewardPointsService service = new RewardPointsService(null);
	Field field = RewardPointsService.class.getDeclaredField("pointsCalculator");
	field.setAccessible(true);
	this.pointsCalculator = (Function<Double, Integer>) field.get(service);
    }

    @Test
    @DisplayName("Check points for different months")
    public void testCalculateRewards_MultipleMonths() {
	List<Transaction> transactions = List.of(new Transaction("C1", "Amar", LocalDate.of(2025, 5, 10), 120),
		new Transaction("C1", "Amit", LocalDate.of(2025, 6, 15), 75),
		new Transaction("C1", "Amit", LocalDate.of(2025, 7, 5), 40));

	when(repository.findByCustomerIdAndDateBetween(eq("C1"), any(), any())).thenReturn(transactions);

	RewardPointsDTO response = rewardService.calculateRewards("C1", LocalDate.of(2025, 5, 1),
		LocalDate.of(2025, 7, 31));

	assertEquals("C1", response.getCustomerId());
	assertEquals("Amar", response.getCustomerName());
	assertEquals(3, response.getMonthlyPoints().size());
	assertEquals(115, response.getTotalPoints());
    }

    @Test
    @DisplayName("Check for non existing customer")
    public void testCalculateRewards_NoTransactions_ShouldThrowException() {
	when(repository.findByCustomerIdAndDateBetween(eq("C1"), any(), any())).thenReturn(List.of());

	CustomerNotFoundException ex = assertThrows(CustomerNotFoundException.class, () -> {
	    rewardService.calculateRewards("C1", LocalDate.of(2025, 4, 1), LocalDate.of(2025, 7, 31));
	});

	assertEquals("No transactions found for customer ID : C1", ex.getMessage());
    }

    @DisplayName("Points validation for different set of inputs")
    @ParameterizedTest(name = "Amount: {0}, Expected Points: {1}")
    @CsvSource({ "40.0, 0", "50.0, 0", "75.0, 25", "100.0, 50", "101.0, 52", "120.0, 90" })
    void shouldCalculatePointsCorrectly(double amount, int expectedPoints) {
	assertEquals(expectedPoints, pointsCalculator.apply(amount));
    }

}
