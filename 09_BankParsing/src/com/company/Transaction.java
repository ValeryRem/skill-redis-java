package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Arrays.asList;

public class Transaction {
    String group;
    double income;
    double outcome;
    private static List<Transaction> transactionList = new ArrayList<>();;

    public Transaction(String group, double income, double outcome) {
        this.group = group;
        this.income = income;
        this.outcome = outcome;
    }

    public static void parseFile(String inputFileName) throws FileNotFoundException {
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
    }

    private static Transaction parseCsvString(String inputScanned) {
        List<String> stringProcessed;
        String lastWord;
        String group;
        double income = 0;
        double outcome = 0;
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
        List<String> stringProcessed;
        String group;
        double income = 0;
        double outcome = 0;
        inputScanned = inputScanned.replace(",", ".");
        stringProcessed = asList(inputScanned.trim().split("\\|"));
        group = stringProcessed.get(2);
        String incomeString = stringProcessed.get(4).trim();
        String outcomeString = stringProcessed.get(5).trim();

        if (incomeString.matches("\\d+\\.\\d+")) {
            income = Double.parseDouble(incomeString);
        }
        if (outcomeString.matches("\\d+\\.\\d+")) {
            outcome = Double.parseDouble(outcomeString);
        }
        return
                new Transaction(group, income, outcome);
    }

    private static boolean isDigitPresent (String input) {
        boolean result = false;
        char currentCharacter;
        for (int i = 0; i < input.length(); i++) {
            currentCharacter = input.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                result = true;
            }
        }
        return result;
    }

    public static void printSumOfIncome(List<Transaction> transactionList) {
        double sum = 0;
        for (Transaction tr : transactionList) {
            sum += tr.income;
        }
        System.out.printf("%s%.2f%s\n", "Total Income: ", sum, " RUR");
    }

    public static void printSumOfExpenses(List<Transaction> transactionList) {
        double sum = 0;
        for (Transaction tr : transactionList) {
            sum += tr.outcome;
        }
        System.out.printf("%s%.2f%s\n", "Total Expenses: ", sum, " RUR");
    }

    public static void printSummaryByGroups(List<Transaction> transactionList) {
        double payment = 0;
        TreeMap<String, Double> expenseTargetsMap = new TreeMap<>();
        for (Transaction tr : transactionList) {
            payment = tr.outcome;
            if (!expenseTargetsMap.containsKey(tr.group)) {
                expenseTargetsMap.put(tr.group, payment);
            } else {
                expenseTargetsMap.put(tr.group, expenseTargetsMap.get(tr.group) + payment);
            }
        }
        System.out.println("\nExpenses by groups:");
        for (Map.Entry<String, Double> entry : expenseTargetsMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public static List<Transaction> getTransactionList() {
        return transactionList;
    }
}
