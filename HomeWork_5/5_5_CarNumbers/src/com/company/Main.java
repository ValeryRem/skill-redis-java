package com.company;

import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static List<String> numberList = new ArrayList<>();
    static HashSet<String> numberHashSet;
    static TreeSet<String> numberTreeSet;
    static TreeMap<Long, String> result = new TreeMap<>();

    public static void main(String[] args) {

        final String STRING1_TO_FIND = "Л888ЛН187";
        final String STRING2_TO_FIND = "А111АА001";
        final String STRING3_TO_FIND = "Я999ЯЯ199";
        List<String> numberListFilled = generateList(numberList);
        numberHashSet = new HashSet<>(numberListFilled);
        numberTreeSet = new TreeSet<>(numberListFilled);
        System.out.println("\nTotally generated " + numberListFilled.size() + " numbers");

        selectByString (STRING1_TO_FIND);
        selectByString(STRING2_TO_FIND);
        selectByString(STRING3_TO_FIND);
    }

    private static List<String> generateList(List<String> listToFill) {
        String[] alphabet = {"А", "Б", "B", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П," +
                "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"};
        for (int i = 0; i < alphabet.length; i++) {
            for (int n = 0; n < alphabet.length; n++) {
                for (int j = 1; j <= 9; j++) {
                    for (int k = 1; k <= 199; k++) {
                        String number;
                        String suffix = formatInt(k);
                        number = alphabet[i] + j + "" + j + "" + j + alphabet[i] + alphabet[n] + suffix;
                        listToFill.add(number);
                    }
                }
            }
        }
        return listToFill;
    }

    private static void searchNumberInSet(String toFind, Collection<String> collection, TreeMap<Long, String> result) {
        boolean isFoundInList;
        long beginList = System.nanoTime();
        isFoundInList = collection.contains(toFind);
        long endList = System.nanoTime();
        String toResult;
        if (isFoundInList) {
            toResult = " ns for search in " + collection.getClass();
            result.put((endList - beginList), toResult);
        } else {
            toResult = " ns for empty search in " + collection.getClass();
            result.put((endList - beginList), toResult);
        }
    }

    private static void findNumberInList(String toFind, List<String> list, TreeMap<Long, String> result, boolean isListSorted) {
        String toResult;
        if (isListSorted) {
            Collections.sort(list);
            long beginListSorted = System.nanoTime();
            int findInListSorted = Collections.binarySearch(list, toFind);
            long endListSorted = System.nanoTime();
            if (findInListSorted >= 0) {
                toResult = " ns for search in sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            } else {
                toResult = " ns for empty search in sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            }
        } else {
            long beginListSorted = System.nanoTime();
            int findInListSorted = Collections.binarySearch(list, toFind);
            long endListSorted = System.nanoTime();
            if (findInListSorted >= 0) {
                toResult = " ns for search in not sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            } else {
                toResult = " ns for empty search in not sorted "  + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            }
        }
    }
    private static void selectByString (String toFind ){
        result = new TreeMap<>();
        searchNumberInSet(toFind, numberHashSet, result);
        searchNumberInSet(toFind, numberTreeSet, result);
        findNumberInList(toFind, numberList, result, false);
        findNumberInList(toFind, numberList, result, true);
        System.out.println("Number to find: " + toFind);
        for (Map.Entry<Long, String> e : result.entrySet()) {
            System.out.println(e);
        }
        System.out.println();
    }

    private static String formatInt(int value) {
        DecimalFormat myFormatter = new DecimalFormat("000");
        return myFormatter.format(value);
    }
}
