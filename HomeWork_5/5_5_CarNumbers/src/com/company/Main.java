package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<String> numberList = new ArrayList<>();
        HashSet<String> numberHashSet = new HashSet<>();
        TreeSet<String> numberTreeSet = new TreeSet<>();
        final String STRING_TO_FIND = "Т888ТТ187";
        generateCollection(numberList);
        generateCollection(numberHashSet);
        generateCollection(numberTreeSet);
        System.out.println("\nTotally generated " + numberList.size() + " numbers:");

        TreeMap<Long, String> result = new TreeMap<>();

        searchNumberInSet(STRING_TO_FIND, numberHashSet, result);
        searchNumberInSet(STRING_TO_FIND, numberTreeSet, result);
        findNumberInList(STRING_TO_FIND, numberList, result, false);
        findNumberInList(STRING_TO_FIND, numberList, result, true);

        for (Map.Entry<Long, String> e : result.entrySet()) {
            System.out.println(e);
        }
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
            toResult = " nanosec: duration of time while number found in " + collection.getClass();
            result.put((endList - beginList), toResult);
        } else {
            toResult = " nanosec: duration of time while number searched but not found in " + collection.getClass();
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
                toResult = " nanosec: duration of time while number found in sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            } else {
                toResult = " nanosec: duration of time while number searched but not found in sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            }
        } else {
            long beginListSorted = System.nanoTime();
            int findInListSorted = Collections.binarySearch(list, toFind);
            long endListSorted = System.nanoTime();
            if (findInListSorted > 0) {
                toResult = " nanosec: duration of time while number found in not sorted " + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            } else {
                toResult = " nanosec: duration of time while number searched but not found in not sorted "  + list.getClass();
                result.put((endListSorted - beginListSorted), toResult);
            }
        }
    }
}
