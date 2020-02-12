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

public class Main {

    public static void main(String[] args) {
        String rainbowText =" Каждый охотник желает знать, где сидит фазан ";
        Rainbow rainbow = new Rainbow();
        rainbow.Reverse(rainbowText);

        Hospital hospital = new Hospital();
        hospital.processTemper();

        Cross cross = new Cross();
        cross.crossDrawing();

    }
}
