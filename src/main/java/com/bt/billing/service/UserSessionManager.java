package com.bt.billing.service;

import com.bt.billing.model.Session;
import com.bt.billing.model.LogEntry;
import com.bt.billing.model.UserBillingInfo;

import java.time.LocalTime;
import java.util.*;

public class UserSessionManager {
    private final Map<String, List<Session>> userSessions;
    private final LocalTime earliestTime;
    private final LocalTime latestTime;

    public UserSessionManager(LocalTime earliestTime, LocalTime latestTime) {
        this.userSessions = new HashMap<>();
        this.earliestTime = earliestTime;
        this.latestTime = latestTime;
    }

    public void processLogEntry(LogEntry entry) {
        userSessions.putIfAbsent(entry.getUsername(), new ArrayList<>());

        List<Session> sessions = userSessions.get(entry.getUsername());

        if (entry.isStart()) {
            sessions.add(new Session(entry.getTimestamp(), latestTime));
        } else {
            boolean matched = false;
            for (int i = sessions.size() - 1; i >= 0; i--) {
                Session session = sessions.get(i);
                if (session.getEndTime().equals(latestTime)) {
                    sessions.set(i, new Session(session.getStartTime(), entry.getTimestamp()));
                    matched = true;
                    break;
                }
            }
            // If no matching start session found, assume the earliest time as start time
            if (!matched) {
                sessions.add(new Session(earliestTime, entry.getTimestamp()));
            }
        }
    }

    public Map<String, UserBillingInfo> calculateBilling() {
        Map<String, UserBillingInfo> billingInfo = new HashMap<>();

        for (Map.Entry<String, List<Session>> entry : userSessions.entrySet()) {
            String username = entry.getKey();
            List<Session> sessions = entry.getValue();
            int sessionCount = sessions.size();
            long totalDuration = sessions.stream()
                    .mapToLong(session -> session.getDuration().getSeconds())
                    .sum();
            billingInfo.put(username, new UserBillingInfo(username, sessionCount, totalDuration));
        }

        return billingInfo;
    }
}
