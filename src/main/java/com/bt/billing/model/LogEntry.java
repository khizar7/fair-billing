package com.bt.billing.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private final LocalTime timestamp;
    private final String username;
    private final boolean isStart;

    public LogEntry(String timestamp, String username, String action) {
        this.timestamp = LocalTime.parse(timestamp, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.username = username;
        this.isStart = action.equalsIgnoreCase("Start");
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public boolean isStart() {
        return isStart;
    }
}
