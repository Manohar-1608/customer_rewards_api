package com.rewardpoints.rewardscalculator.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;
    private String customerName;
    private LocalDate date;
    private double amount;

    public Transaction() {
    }

    public Transaction(String customerId, String customerName, LocalDate date, double amount) {
	this.customerId = customerId;
	this.customerName = customerName;
	this.date = date;
	this.amount = amount;
    }

    public Long getId() {
	return id;
    }

    public String getCustomerId() {
	return customerId;
    }

    public String getCustomerName() {
	return customerName;
    }

    public LocalDate getDate() {
	return date;
    }

    public double getAmount() {
	return amount;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }
}