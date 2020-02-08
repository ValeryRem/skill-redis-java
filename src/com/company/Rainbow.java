package com.company;

public class Rainbow {
    void Reverse(String text) {
        String[] rainbowArray = (text.trim()).split(",?\\s+");
        System.out.println("\tRainbow Block");
        for (
                int i = rainbowArray.length - 1;
                i >= 0; i--) {
            System.out.println(rainbowArray[i]);
        }
    }
}
