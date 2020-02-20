package com.company;

import java.util.*;

public class Main {
    static List<String> numberList = new ArrayList<>();
    static HashSet<String> numberHashSet; //= new HashSet<>(numberList);
    static TreeSet<String> numberTreeSet;
    static TreeMap<Long, String> result = new TreeMap<>();

    public static void main(String[] args) {

        final String STRING1_TO_FIND = "Т888ТТ187";
        final String STRING2_TO_FIND = "А111АА01";
        final String STRING3_TO_FIND = "Я999ЯЯ190";
        generateCollection(numberList);
        numberHashSet = new HashSet<>(numberList);
        numberTreeSet = new TreeSet<>(numberList);
        System.out.println("\nTotally generated " + numberList.size() + " numbers");

        selectByString (STRING1_TO_FIND);
        selectByString(STRING2_TO_FIND);
        selectByString(STRING3_TO_FIND);
    }

    private static void generateCollection(Collection<String> object) {
        String[] alphabet = {"А", "Б", "B", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П," +
                "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"};
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 1; j < 10; j++) {
                for (int k = 1; k <= 199; k++) {
                    String number;
                    if (k < 10) {
                        number = alphabet[i] + j + "" + j + "" + j + alphabet[i] + alphabet[i] + "0" + k;
                    } else {
                        number = alphabet[i] + j + "" + j + "" + j + alphabet[i] + alphabet[i] + k;
                    }
                    object.add(number);
                }
            }
        }
    }

    private static void searchNumberInSet(String toFind, Collection<String> collection, TreeMap<Long, String> result) {
        boolean isFoundInList;
        long beginList = System.nanoTime();
        isFoundInList = collection.contains(toFind);
        long endList = System.nanoTime();
        String toResult;
        if (isFoundInList) {
            toResult = " nanosec for search in " + collection.getClass();
            result.put((endList - beginList), toResult);
        } else {
            toResult = " nanosec for search when number is not found in " + collection.getClass();
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
            if (findInListSorted > 0) {
                toResult = " nanosec for search in sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            } else {
                toResult = " nanosec for search in sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            }
        } else {
            long beginListSorted = System.nanoTime();
            int findInListSorted = Collections.binarySearch(list, toFind);
            long endListSorted = System.nanoTime();
            if (findInListSorted > 0) {
                toResult = " nanosec for search in not sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            } else {
                toResult = " nanosec for search in not sorted "  + list.getClass();
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
}
