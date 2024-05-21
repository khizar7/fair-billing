package com.bt.billing.model;

import java.time.Duration;
import java.time.LocalTime;

public class Session {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Session(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }
}
