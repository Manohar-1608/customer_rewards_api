package com.rewardpoints.rewardscalculator.enums;

public enum RewardPoint {
    ZERO_POINT(0),
    TWO_POINTS(2);

    private final int value;

    RewardPoint(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
