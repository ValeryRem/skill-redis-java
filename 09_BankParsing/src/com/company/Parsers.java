package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

public class Parsers {

    public static List<Transaction> parseFile(String inputFileName) throws FileNotFoundException {
        List<Transaction> transactionList = new ArrayList<>();
        Scanner scanner1 = new Scanner(new File(inputFileName));
        while (scanner1.hasNext()) {
            String inputScanned = scanner1.nextLine();
            if (isDigitPresent(inputScanned)) {
                if (inputFileName.endsWith(".csv")) {
                    transactionList.add(parseCsvString(inputScanned));
                } else if (inputFileName.endsWith(".org")) {
                    transactionList.add(parseOrgString(inputScanned));
                } else {
                    throw new RuntimeException("Непонятный формат файла: " + inputFileName);
                }
            }
        }
        scanner1.close();
        return transactionList;
    }

    private static Transaction parseCsvString(String inputScanned) {
        List<String> stringProcessed;
        String lastWord;
        String group;
        double income;
        double outcome;
        stringProcessed = asList(inputScanned.split("\\s+"));
        lastWord = stringProcessed.get(stringProcessed.size() - 1);
        group = stringProcessed.get(2);
        if (lastWord.contains("\"")) {
            String[] lastWordArray = lastWord.split("\"");
            lastWordArray[1] = lastWordArray[1].replace(",", ".").replaceAll("\"", "");
            lastWord = lastWordArray[0] + lastWordArray[1];
        }
        income = Double.parseDouble(lastWord.split(",")[1]);
        outcome = Double.parseDouble(lastWord.split(",")[2]);
        return
                new Transaction(group, income, outcome);
    }

    private static Transaction parseOrgString(String inputScanned) {
        String[] stringProcessed;

        inputScanned = inputScanned.replace(",", ".");
        stringProcessed = inputScanned.trim().split("\\|");
        String group = stringProcessed[2];
        String incomeString = stringProcessed[4].trim();
        String outcomeString = stringProcessed[5].trim();

        double income = 0;
        if (incomeString.matches("\\d+\\.\\d+")) {
            income = Double.parseDouble(incomeString);
        }
        double outcome = 0;
        if (outcomeString.matches("\\d+\\.\\d+")) {
            outcome = Double.parseDouble(outcomeString);
        }
        return
                new Transaction(group, income, outcome);
    }

    private static boolean isDigitPresent (String input) {
        boolean result = false;
        for (int i = 0; i < input.length(); i++) {
            char currentCharacter = input.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
