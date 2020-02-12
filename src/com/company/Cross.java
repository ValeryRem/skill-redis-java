package com.company;

public class Cross {
    void crossDrawing() {
        System.out.println("\n\t Block of Crosses");
        final int LENGTH_OF_BOX = 11;
        String[][] boxOfCrosses = new String[LENGTH_OF_BOX][LENGTH_OF_BOX];
        for (int i = 0; i < LENGTH_OF_BOX; i++) {
            System.out.println();
            for (int j = 0; j < LENGTH_OF_BOX; j++) {
                boxOfCrosses[i][j] = (j == i || j == LENGTH_OF_BOX - 1 - i) ? "X" : " ";
                System.out.print(boxOfCrosses[i][j]);
            }
        }
    }
}
