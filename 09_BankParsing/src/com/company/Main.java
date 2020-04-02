/**
 * Написать код парсинга банковской выписки (файл movementsList.csv). Код должен выводить сводную информацию
 * по этой выписке: общий приход, общий расход, а также разбивку расходов по назначению платежей.
 */
package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static double totalIncome;
    private static double totalExpenses;

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\valery\\Desktop\\java_basics\\09_BankParsing\\src\\com\\movementList.csv";
        int indexOfSum;
        List<ArrayList<String>> incomeList = new ArrayList<>();
        List<ArrayList<String>> expenseList = new ArrayList<>();
        TreeMap<String, Double> expenseTargets = new TreeMap<>();
        try {
            Scanner scanner = new Scanner(new File(csvFile));
            String lastWord;
            double sumToAdd;
            while (scanner.hasNext()) {
                ArrayList<String> stringList = parseLine(scanner.nextLine());
                if (stringList.contains("ALFA_MOBILE>MOSCOW")) {
                    incomeList.add(stringList);
                    indexOfSum = stringList.lastIndexOf("RUR") - 1;
                    totalIncome += Double.parseDouble(stringList.get(indexOfSum));
                } else {
                    expenseList.add(stringList);
                }
            }

            for (int i = 1; i < expenseList.size(); i++) {
                lastWord = expenseList.get(i).get(expenseList.get(i).size() - 1);
                if (lastWord.contains("\"")) {
                    sumToAdd = Double.parseDouble(lastWord.split("\"")[1].replace(",", "."));

                } else {
                    sumToAdd = Double.parseDouble(lastWord.split(",")[lastWord.split(",").length - 1]);
                }
                if (!expenseTargets.containsKey(expenseList.get(i).get(2))) {
                    expenseTargets.put(expenseList.get(i).get(2), sumToAdd);
                } else {
                    expenseTargets.put(expenseList.get(i).get(2), expenseTargets.get(expenseList.get(i).get(2)) + sumToAdd);
                }
                totalExpenses += sumToAdd;
            }
            scanner.close();
            System.out.printf("%s%.2f%n", "Total Income: ", totalIncome);
            System.out.printf("%s%.2f%n", "Total Expenses: ", totalExpenses);
            System.out.println("incomeList.size(): " + incomeList.size());
            System.out.println("expenseList.size(): " + expenseList.size());
            System.out.println("Lis of expense targets, size: " + expenseTargets.size());
            System.out.println("\nJoint expenses by targets:");

            for (Map.Entry<String, Double> entry : expenseTargets.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue() + " RUR");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<String> parseLine(String csvLine) throws NullPointerException {
        ArrayList<String> result = new ArrayList<>();
        if (csvLine.matches("[\\w\\W]+")) {
            String[] words = csvLine.split("\\s+");
            Collections.addAll(result, words);
        } else {
            System.err.println("Empty line!");
        }
        return result;
    }
}
