package com.rewardpoints.rewardscalculator.enums;

public enum RewardLimit {

    LOWER(50),
    UPPER(100);

    private final int value;

    RewardLimit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
