package com.company;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TreeMap<String, String> telBook = new TreeMap<>() {{
            put("097809987", "Pot");
            put("454", "Milli");
            put("09809879786", "Steve");
            put("5769087099", "Lorhen");
            put("3453463", "Tobby");
        }};

        for (; ; ) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Insert the phone number or the name, or the order LIST");
            String input = scanner.nextLine();
            input = input.replaceAll("\\W", "");
            if (input.equals("LIST")) {
                List<String> listOfValues = new ArrayList<>(telBook.values());
                Collections.sort(listOfValues);
                for (int i = 0; i < listOfValues.size(); i++) {
                    for (Map.Entry<String, String> pair : telBook.entrySet()) {
                        if (listOfValues.get(i).equals(pair.getValue())) {
                            System.out.println(pair);
                        }
                    }
                }
            } else {
                if (input.matches("\\d+") && telBook.containsKey(input)) {
                    System.out.println(telBook.get(input));
                } else if (telBook.containsValue(input)) {
                    for (Map.Entry<String, String> pair : telBook.entrySet()) {
                        if (input.equals(pair.getValue())) {
                            System.out.println(pair.getKey());
                        }
                    }
                } else {
                    if ((input.trim()).matches("\\d+")) {
                        System.out.println("Insert the name");
                        String name = scanner.nextLine();
                        name = name.replaceAll("\\W", "");
                        telBook.put(input, name);
                        System.out.println("New contact added");
                    } else if (input.matches("\\w+")) {
                        System.out.println("Insert the phone number");
                        String number = scanner.nextLine();
                        number = number.replaceAll("\\D", "");
                        telBook.put(number, input);
                        System.out.println("New contact added");
                    } else {
                        System.out.println("The input is wrong! Correct and repeat input.");
                    }
                }
            }
        }
    }
}
