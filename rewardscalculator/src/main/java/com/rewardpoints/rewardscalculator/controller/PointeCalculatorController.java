package com.rewardpoints.rewardscalculator.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/rewardpoints")
public class PointeCalculatorController {

    @Autowired
    private RewardPointsService rewardService;

    @GetMapping(value = "/countpoints")
    public ResponseEntity<RewardPointsDTO> getRewards(@RequestParam String customerId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
	return new ResponseEntity<>(rewardService.calculateRewards(customerId, from, to), HttpStatus.OK);
    }
}
