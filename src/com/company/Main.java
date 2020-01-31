/**
 * Повторить создание массива и заполнение цветами радуги, как показано в видео, а затем написать код, переворачивающий этот массив.
 * Создать массив с температурами 30-ти пациентов (от 32 до 40 градусов). Написать код, рассчитывающий среднюю температуру по больнице
 * и количество здоровых пациентов (с температурой от 36,2 до 36,9).
 * Создать с помощью циклов массив массивов строк таким образом, чтобы при его распечатке в консоли печатался крестик из крестиков:
 *
 * x     x
 *  x   x
 *   x x
 *    x
 *   x x
 *  x   x
 * x     x
 */

package com.company;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {

    public static void main(String[] args) {
        //Rainbow block
        String rainbow = " Каждый охотник желает знать, где сидит фазан ";
        String[] rainbowArray = (rainbow.trim()).split(",?\\s+");
        String[] rainbowArrayRevers = new String[rainbowArray.length];
        for (int i = 0; i < rainbowArray.length; i++) {
            rainbowArrayRevers[i] = rainbowArray[rainbowArray.length - 1 - i];
        }
        System.out.println("\tRainbow Block");
        for (String word: rainbowArrayRevers) {
            System.out.println(word);
        }
        // Hospital block
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

        System.out.println("Average temperature: " + averageTemperature + "\nNumber of persons with normal temperature: " + countOfHealthyPersons);

   //Cross block
        System.out.println("\n\t Block of Crosses");
        final int LENGTH_OF_BOX = 11;
        String[][] boxOfCrosses = new String[LENGTH_OF_BOX][LENGTH_OF_BOX];
        for(int i = 0; i < LENGTH_OF_BOX; i++){
            System.out.println();
            for(int j = 0; j < LENGTH_OF_BOX; j++){
                boxOfCrosses[i][j] = (j == i || j == LENGTH_OF_BOX - 1 - i) ? "X": " ";
                System.out.print(boxOfCrosses[i][j]);
            }
        }
    }
}
