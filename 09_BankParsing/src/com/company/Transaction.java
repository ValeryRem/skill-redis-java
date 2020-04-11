package com.company;
import java.util.*;

public class Transaction {
    String group;
    double income;
    double outcome;
    private static List<Transaction> transactionList = new ArrayList<>();

    public Transaction(String group, double income, double outcome) {
        this.group = group;
        this.income = income;
        this.outcome = outcome;
    }

    public static List<Transaction> getTransactionList() {
        return transactionList;
    }

    public static void setTransactionList(List<Transaction> transactionList) {
        Transaction.transactionList = transactionList;
    }

//    public Transaction(String group) {
//        this.group = group;
//    }

    public String getGroup() {
        return group;
    }

    public double getIncome() {
        return income;
    }

    public double getOutcome() {
        return outcome;
    }
}
