package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
        transactionList.stream()
                .collect(Collectors.toMap(Transaction::getGroup, Transaction::getOutcome, Double::sum))
                .entrySet()
                .forEach(System.out::println);
    }

    public static void printJointSummaryByGroups(List<Transaction> transactionList) {
//        Map <String, Double> incomeMap = transactionList.stream()
//                .collect(Collectors.toMap(Transaction::getGroup, Transaction::getIncome, Double::sum));
//        Map <String, Double> outcomeMap = transactionList.stream()
//                        .collect(Collectors.toMap(Transaction::getGroup, Transaction::getOutcome, Double::sum));
//        Map <String, String> resultMap = new HashMap<>();
//        for (Map.Entry<String, Double> e : incomeMap.entrySet())
//            resultMap.merge(e.getKey(), e.getValue().toString(), String::concat);
//
//        for (Map.Entry<String, Double> e: outcomeMap.entrySet()) {
//            resultMap.merge(e.getKey(), e.getValue().toString(), (x, y) -> String.join(" - ", "  in: " + x, "out: " + y));
//        }
//
//        resultMap.entrySet().forEach(System.out::println);
/////////////////////////////////////////////////////////////////////////////
        transactionList.stream()
                .collect(Collectors.groupingBy(Transaction::getGroup,
                        Collectors.mapping(Summary::fromTransaction,
                        Collectors.reducing(Summary::merge)
                )))
                .forEach((s, sum) -> System.out.println(s + " in: " + sum.get().income + " - out: " + sum.get().withdraw));
    }

    private static class Summary {
        double income;
        double withdraw;

        Summary(double income, double withdraw) {
            this.income = income;
            this.withdraw = withdraw;
        }

        // сложение сумм
        static Summary merge(Summary s1, Summary s2) {
            return new Summary(s1.income + s2.income, s1.withdraw + s2.withdraw);
        }
        // mapper - конвертация из Transaction
        static Summary fromTransaction(Transaction t) {
            return new Summary(t.getIncome(), t.getOutcome());
        }

        public double getIncome() {
            return income;
        }

        public double getWithdraw() {
            return withdraw;
        }
    }
}
