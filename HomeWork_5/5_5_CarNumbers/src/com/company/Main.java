package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String[] alphabet = {"А", "Б", "B", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П," +
                "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"};
        List<String> numberList = new ArrayList();
        HashSet<String> numberHashSet = new HashSet<>();
        TreeSet <String> numberTreeSet = new TreeSet<>();
        final String STRING_TO_FIND = "Т888ТТ187";
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 1; j < 10; j++) {
                for (int k = 1; k <= 199; k++) {
                    String number;
                    if (k < 10) {
                        number = alphabet[i] + j + "" + j + "" + j + alphabet[i] + alphabet[i] + "0" + k;
                    } else {
                        number = alphabet[i] + j + "" + j + "" + j + alphabet[i] + alphabet[i] + k;
                    }
                        numberList.add(number);
                        numberHashSet.add(number);
                        numberTreeSet.add(number);
                }
            }
        }
        System.out.println("\nTotally generated " + numberList.size() + " numbers:");
        for (String s : numberList) {
            System.out.println(s);
        }
        boolean isFoundInList;
        boolean isFoundInHashSet;
        boolean isFoundInTreeSet;
        long beginList = System.nanoTime();
        // direct search in List
        isFoundInList = numberList.contains(STRING_TO_FIND);
        long endList = System.nanoTime();
        if (isFoundInList) {
            System.out.println("The number is found in not sorted List in " + (endList - beginList) + " nanosec.");
        } else {
            System.out.println("The number is NOT found in not sorted List in " + (endList - beginList) + " nanosec.");
        }
        // search in sorted List
        Collections.sort(numberList);
        long beginListSorted = System.nanoTime();
        int findInListSorted = Collections.binarySearch(numberList, STRING_TO_FIND);
        long endListSorted = System.nanoTime();
        if (findInListSorted > 0) {
            System.out.println("The number is found in sorted List in " + (endListSorted - beginListSorted) + " nanosec.");
        } else {
            System.out.println("The number is NOT found in sorted List in " + (endListSorted - beginListSorted) + " nanosec.");
        }
        // search in HashSet
        long beginHashSet = System.nanoTime();
        isFoundInHashSet = numberHashSet.contains(STRING_TO_FIND);
        long endHashSet = System.nanoTime();
        if (isFoundInHashSet) {
            System.out.println("The number is found in HashSet in " + (endHashSet - beginHashSet) + " nanosec.");
        } else {
            System.out.println("The number is NOT found in HashSet in " + (endHashSet - beginHashSet) + " nanosec.");
        }
        // search in TreeSet
        long beginTreeSet = System.nanoTime();
        isFoundInTreeSet = numberHashSet.contains(STRING_TO_FIND);
        long endTreeSet = System.nanoTime();
        if (isFoundInTreeSet) {
            System.out.println("The number is found in TreeSet in " + (endTreeSet - beginTreeSet) + " nanosec.");
        } else {
            System.out.println("The number is NOT found in TreeSet in " + (endTreeSet - beginTreeSet) + " nanosec.");
        }
    }
}
