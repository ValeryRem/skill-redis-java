/**
 * 1) Написать код парсинга страницы Википедии “Список станций Московского метрополитена”, который будет
 * создавать JSON-файл со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json,
 * приложен к домашнему заданию)
 * Также пропарсить и вывести в JSON-файл переходы между станциями.
 * 2) Написать код, который прочитает созданный JSON-файл и напечатает количества станций на каждой линии.
 */

package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        String origin = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9" +
//                "_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1" +
//                "%82%D0%B5%D0%BD%D0%B0#%D0%A1%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B8_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_" +
//                "%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

//        try (InputStream in = new URL(origin).openStream()) {
//            Files.copy(in, Paths.get("C:\\Users\\valery\\Desktop\\java_basics\\09_MoscowMetroJson\\metro.html"), StandardCopyOption.REPLACE_EXISTING);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // json of Stations
        Map<String, List<String>> stationMap = new TreeMap<>();
        ParserOfStations parserOfStations = new ParserOfStations();
        String cssQuery = "table:has(span[title='Переход на станцию Бульвар Рокоссовского Московского центрального кольца'])";
//        String cssQueryRing = "table:has(th['14'])";
        String origin = "src/metro.html";
        parserOfStations.parsingMetroMap(origin, stationMap, cssQuery);
        Connection connection = new Connection();
        HashSet<List<Connection>> listHashSet = connection.connectionSetGenerate(parserOfStations.getListIndex());

        Presenter presenter = new Presenter();
        presenter.presentStations(stationMap);
        presenter.presentConnections(listHashSet);
    }
}
