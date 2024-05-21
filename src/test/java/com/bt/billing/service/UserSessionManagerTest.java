package com.bt.billing.service;

import com.bt.billing.model.LogEntry;
import com.bt.billing.model.UserBillingInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSessionManagerTest {
    private UserSessionManager sessionManager;

    @BeforeEach
    public void setUp() {
        LocalTime earliestTime = LocalTime.parse("00:00:00");
        LocalTime latestTime = LocalTime.parse("23:59:59");
        sessionManager = new UserSessionManager(earliestTime, latestTime);
    }

    @Test
    public void processLogEntryAndCalculateBilling() {
        sessionManager.processLogEntry(new LogEntry("14:02:03", "ALICE99", "Start"));
        sessionManager.processLogEntry(new LogEntry("14:02:34", "ALICE99", "End"));
        sessionManager.processLogEntry(new LogEntry("14:03:02", "CHARLIE", "Start"));
        sessionManager.processLogEntry(new LogEntry("14:03:37", "CHARLIE", "End"));

        Map<String, UserBillingInfo> billingInfo = sessionManager.calculateBilling();

        assertEquals(1, billingInfo.get("ALICE99").getSessionCount());
        assertEquals(31, billingInfo.get("ALICE99").getTotalDuration());
        assertEquals(1, billingInfo.get("CHARLIE").getSessionCount());
        assertEquals(35, billingInfo.get("CHARLIE").getTotalDuration());
    }

    @Test
    public void unmatchedStartSession() {
        sessionManager.processLogEntry(new LogEntry("14:02:03", "ALICE99", "Start"));

        Map<String, UserBillingInfo> billingInfo = sessionManager.calculateBilling();

        assertEquals(1, billingInfo.get("ALICE99").getSessionCount());
        assertEquals(Duration.between(LocalTime.parse("14:02:03"), LocalTime.parse("23:59:59")).getSeconds(), billingInfo.get("ALICE99").getTotalDuration());
    }

    @Test
    public void unmatchedEndSession() {
        sessionManager.processLogEntry(new LogEntry("14:02:03", "ALICE99", "End"));

        Map<String, UserBillingInfo> billingInfo = sessionManager.calculateBilling();

        assertEquals(1, billingInfo.get("ALICE99").getSessionCount());
        assertEquals(Duration.between(LocalTime.parse("00:00:00"), LocalTime.parse("14:02:03")).getSeconds(), billingInfo.get("ALICE99").getTotalDuration());
    }
}
