/*
Используя библиотеку airport.jar, распечатать время вылета и модели самолётов, вылетающие в ближайшие 2 часа.
 */

package com.company;

import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

public class Main {

    public static void main(String[] args) {
        Airport item = Airport.getInstance();
        List<Terminal> terminalList = item.getTerminals();
        List<Aircraft> listAircrafts = item.getAllAircrafts();
        System.out.println("Number of the airplanes available: " + listAircrafts.size());
//        listAircrafts.forEach(System.out::println);
        for (int i = 0; i < terminalList.size(); i++) {
            System.out.println("\nFlights within two hrs DEPARTURE at the terminal: " + terminalList.get(i).getName());
            List<Flight> flightList = terminalList.get(i).getFlights();
            long currentTime = System.currentTimeMillis();
            flightList.stream()
                    .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
                    .filter(flight -> flight.getDate().getTime() - currentTime > 0 && flight.getDate().getTime() - currentTime <= 7200000L)
                    .forEach(flight -> System.out.println(flight.getAircraft() + " - " + flight.getDate()));
        }
//        int i = 0;
//        while (i < listAircrafts.size()) {
//            System.out.println("Aircraft model: " + listAircrafts.get(i));
//            i++;
//        }
    }
}
