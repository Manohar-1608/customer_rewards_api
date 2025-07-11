package com.rewardpoints.rewardscalculator.dto;

import java.util.Map;

public class RewardPointsDTO {

    private String customerId;
    private String customerName;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

    public RewardPointsDTO(String customerId, String customerName, Map<String, Integer> monthlyPoints,
	    int totalPoints) {
	this.customerId = customerId;
	this.customerName = customerName;
	this.monthlyPoints = monthlyPoints;
	this.totalPoints = totalPoints;
    }

    public RewardPointsDTO() {

    }

    public String getCustomerId() {
	return customerId;
    }

    public String getCustomerName() {
	return customerName;
    }

    public Map<String, Integer> getMonthlyPoints() {
	return monthlyPoints;
    }

    public int getTotalPoints() {
	return totalPoints;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;

    }

    public void setTotalPoints(int totalPoints) {
	this.totalPoints = totalPoints;

    }

}
