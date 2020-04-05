package com.company;

import java.util.List;
import java.util.TreeMap;

import static java.util.Arrays.*;

public class Transactions {

    public List<String> parseLine(String csvLine) {
        String lastWord;
        List<String> strings;
        if (csvLine.matches("[\\w\\W]+")) {
            strings = asList(csvLine.split("\\s+"));
            lastWord = strings.get(strings.size() - 1);
            if (lastWord.contains("\"")) {
                String[] lastWordArray = lastWord.split("\"");
                lastWordArray[1] = lastWordArray[1].replace(",", ".").replaceAll("\"", "");
                lastWord = lastWordArray[0] + lastWordArray[1];
            }
            strings.set(strings.size() - 1, lastWord);
            return
                    strings;
        } else {
            System.err.println("Empty line!");
            return null;
        }
    }

    public void completeInOutLists(List<List<String>> incomeList, List<List<String>> expenseList, List<String> stringList) {
        String lastWord = stringList.get(stringList.size() - 1);
        if (lastWord.split(",").length == 3) {
            if (lastWord.split(",")[1].matches("0")) {
                expenseList.add(stringList);
            } else {
                incomeList.add(stringList);
            }
        }
    }

    public double getSumOfIncome(List<List<String>> incomeList) {
        double sum = 0.0;
        for (List<String> incomeString : incomeList) {
            sum += Integer.parseInt(incomeString.get(incomeString.size() - 1).split(",")[1].trim());
        }
        return sum;
    }

    public double getSumOfExpenses(List<List<String>> expenseList) {
        double sum = 0.0;
        for (List<String> expenseString : expenseList) {
            sum += Double.parseDouble(expenseString.get(expenseString.size() - 1).split(",")[2]);
        }
        return sum;
    }

    public TreeMap<String, Double> getExpenseTargetsMap(List<List<String>> expenseList) {
        double payment = 0;
        TreeMap<String, Double> expenseTargetsMap = new TreeMap<>();
        for (List<String> strings : expenseList) {
            payment = Double.parseDouble(strings.get(strings.size() - 1).split(",")[2]);
            if (!expenseTargetsMap.containsKey(strings.get(2))) {
                expenseTargetsMap.put(strings.get(2), payment);
            } else {
                expenseTargetsMap.put(strings.get(2), expenseTargetsMap.get(strings.get(2)) + payment);
            }
        }
        return expenseTargetsMap;
    }
}
