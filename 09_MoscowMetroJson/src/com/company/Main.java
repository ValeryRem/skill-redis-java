/**
 * 1) Написать код парсинга страницы Википедии “Список станций Московского метрополитена”, который будет
 * создавать JSON-файл со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json,
 * приложен к домашнему заданию)
 * Также пропарсить и вывести в JSON-файл переходы между станциями.
 * 2) Написать код, который прочитает созданный JSON-файл и напечатает количества станций на каждой линии.
 */
package com.company;
import com.google.gson.GsonBuilder;

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
//        Map<String, List<String>> stationMap = new TreeMap<>();
        ParserOfStations parserOfStations = new ParserOfStations();
        String cssQuery = "table";//:has(span[title='Переход на станцию Бульвар Рокоссовского Московского центрального кольца'])";
        String origin = "src/metro.html";
        StationIndex stationIndex = parserOfStations.parsingMetroMap(origin, cssQuery);
//        System.out.println("listIndex.size(): " + parserOfStations.listIndex.size());
//        parserOfStations.getListIndex().forEach(x -> System.out.println(x.name));

//        parserOfStations.listIndex.stream().map(x -> (x.name + " - " + x.line)).forEach(System.out::println);
//        parserOfStations.connections.forEach(x -> {
//                    System.out.print(x.prime.name);
//                    x.stationsList.forEach(System.out::println);
//        });

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(stationIndex);
        System.out.println(json);
//        for (Entry entry : parserOfStations.stationsMap.entrySet()) {
//            System.out.println("Key: " + entry.getKey());
//            System.out.println("\t Value: " + entry.getValue());
//        }


//        String json1 = new GsonBuilder().setPrettyPrinting().create().toJson(stationsMap);
//        System.out.println(json1);
    }
}
