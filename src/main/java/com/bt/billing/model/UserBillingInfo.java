package com.bt.billing.model;

public class UserBillingInfo {
    private final String username;
    private final int sessionCount;
    private final long totalDuration;

    public UserBillingInfo(String username, int sessionCount, long totalDuration) {
        this.username = username;
        this.sessionCount = sessionCount;
        this.totalDuration = totalDuration;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    @Override
    public String toString() {
        return username + " " + sessionCount + " " + totalDuration;
    }
}

