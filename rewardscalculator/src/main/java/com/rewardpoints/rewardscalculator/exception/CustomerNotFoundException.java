package com.rewardpoints.rewardscalculator.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
	super(message);
    }
}
