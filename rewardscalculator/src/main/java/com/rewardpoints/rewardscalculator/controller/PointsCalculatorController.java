package com.rewardpoints.rewardscalculator.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewardpoints.rewardscalculator.dto.RewardPointsDTO;
import com.rewardpoints.rewardscalculator.rewardservice.RewardPointsService;

@RestController
@RequestMapping("/api/reward-points")
public class PointsCalculatorController {

    private final RewardPointsService rewardService;

    public PointsCalculatorController(RewardPointsService rewardService) {
	this.rewardService = rewardService;
    }

    @GetMapping(value = "/count-points")
    public ResponseEntity<RewardPointsDTO> getRewards(@RequestParam String customerId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

	if (customerId == null || customerId.isBlank()) {
	    throw new IllegalArgumentException("Customer ID must not be null or blank.");
	}

	if (from == null || to == null || from.isAfter(to)) {
	    throw new IllegalArgumentException("Invalid date range: 'from' must be before 'to'. ");
	}

	return new ResponseEntity<>(rewardService.calculateRewards(customerId, from, to), HttpStatus.ACCEPTED);
    }
}
