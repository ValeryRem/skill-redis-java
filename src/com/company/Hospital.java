package com.company;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Hospital {
    void processTemper (){
        final MathContext DECIMAL3 = new MathContext(3, RoundingMode.HALF_EVEN);
        System.out.println("\n \t Hospital Block");
        final int TEMP_MIN = 32;
        final int TEMP_MAX = 40;
        final double TEMP_MIN_NORM = 36.2;
        final double TEMP_MAX_NORM = 36.9;
        double[] hospitalTemper = new double[30];
        double sumOfTemp = 0;
        int countOfHealthyPersons = 0;
        for (int i = 0; i < hospitalTemper.length; i++){
            hospitalTemper[i] = TEMP_MIN + (TEMP_MAX - TEMP_MIN) * Math.random();
            sumOfTemp = sumOfTemp + hospitalTemper[i];
            if(hospitalTemper[i] >= TEMP_MIN_NORM && hospitalTemper[i] <= TEMP_MAX_NORM){
                countOfHealthyPersons++;
            }
            System.out.println(hospitalTemper[i]);
        }
        double middleTemp = sumOfTemp / hospitalTemper.length;
        BigDecimal averageTemperature = BigDecimal.valueOf(middleTemp).round(DECIMAL3);
        System.out.println("Average temperature: " + averageTemperature + "\nNumber of persons with normal temperature: "
                + countOfHealthyPersons);
    }
}
