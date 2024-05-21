package com.bt.billing;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class BillingAppTest {

    @Test
    public void billingApplication() {
        String testLogFilePath = "src/test/resources/input.txt";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BillingApp.main(new String[]{testLogFilePath});

        String expectedOutput = "ALICE99 4 240\nCHARLIE 3 37\n";
        String actualOutput = outContent.toString();

        List<String> expectedLines = Arrays.asList(expectedOutput.split("\\R"));
        List<String> actualLines = Arrays.asList(actualOutput.split("\\R"));
        assertLinesMatch(expectedLines, actualLines);
    }
}