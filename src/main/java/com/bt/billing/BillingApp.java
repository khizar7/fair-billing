package com.bt.billing;

import com.bt.billing.model.LogEntry;
import com.bt.billing.service.UserSessionManager;
import com.bt.billing.model.UserBillingInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;

public class BillingApp {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FairBilling <path_to_log_file>");
            System.exit(1);
        }

        String filePath = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            LocalTime earliestTime = LocalTime.MAX;
            LocalTime latestTime = LocalTime.MIN;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    LocalTime timestamp = LocalTime.parse(parts[0]);
                    if (timestamp.isBefore(earliestTime)) {
                        earliestTime = timestamp;
                    }
                    if (timestamp.isAfter(latestTime)) {
                        latestTime = timestamp;
                    }
                }
            }

            UserSessionManager sessionManager = new UserSessionManager(earliestTime, latestTime);

            try (BufferedReader readerAgain = new BufferedReader(new FileReader(filePath))) {
                while ((line = readerAgain.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        LogEntry entry = new LogEntry(parts[0], parts[1], parts[2]);
                        sessionManager.processLogEntry(entry);
                    }
                }
            }

            Map<String, UserBillingInfo> billingInfo = sessionManager.calculateBilling();

            billingInfo.values().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
