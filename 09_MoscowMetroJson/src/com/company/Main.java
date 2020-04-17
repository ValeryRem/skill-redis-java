/**
 * 1) Написать код парсинга страницы Википедии “Список станций Московского метрополитена”, который будет
 * создавать JSON-файл со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json,
 * приложен к домашнему заданию)
 * Также пропарсить и вывести в JSON-файл переходы между станциями.
 * 2) Написать код, который прочитает созданный JSON-файл и напечатает количества станций на каждой линии.
 */

package com.company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String origin = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9" +
                "_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1" +
                "%82%D0%B5%D0%BD%D0%B0#%D0%A1%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B8_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_" +
                "%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
        // json of Stations
        Map<String, Set<Station>> stationMap = new TreeMap<>();
        ParserOfStations parserOfStations = new ParserOfStations();
        parserOfStations.parsingMetroMap(origin, stationMap);
        System.out.println("\nNumber of lines: " + stationMap.size());
        String jsonStr = new Gson().toJson(stationMap);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonStr);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);

        // json of connections
        Map<String, Set<Connection>> mapOfConnections = new TreeMap<>();
        ParserOfConnections parserOfConnections = new ParserOfConnections();
        parserOfConnections.parsingConnections(origin, mapOfConnections);
        String jsonString = new Gson().toJson(mapOfConnections);
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jprsr = new JsonParser();
        JsonElement jelm = jprsr.parse(jsonString);
        String connectionsJSON = gs.toJson(jelm);
        System.out.println("\n///////////////////////////////////////////////////////////\n");
        System.out.println(connectionsJSON);

        //        mapOfConnections.entrySet().forEach(System.out::println);
//        System.out.println("Number of connections: " + mapOfConnections.values().size());
    }
}
