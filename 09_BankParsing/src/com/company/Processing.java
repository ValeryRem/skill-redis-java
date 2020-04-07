package com.company;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Processing {

    public static void printSumOfIncome(List<Transaction> transactionList) {
        double sum = transactionList.stream().mapToDouble(t -> t.income).sum();
        System.out.printf("%s%.2f%s\n", "Total Income: ", sum, " RUR");
    }

    public static void printSumOfExpenses(List<Transaction> transactionList) {
        double sum = transactionList.stream().mapToDouble(t -> t.income).sum();
        System.out.printf("%s%.2f%s\n", "Total Expenses: ", sum, " RUR");
    }

    public static void printSummaryByGroups(List<Transaction> transactionList) {
        double payment;
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
}
