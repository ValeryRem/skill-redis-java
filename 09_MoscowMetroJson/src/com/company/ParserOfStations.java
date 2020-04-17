package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ParserOfStations {
    public ParserOfStations() {
    }

    public void parsingMetroMap(String origin, Map<String, Set<Station>> setMap) {
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(2_500_000).get();
            Element table = doc.select("table:has(a[title='Бульвар Рокоссовского (станция метро)'])").first();
            Elements rows = table.select("tr"); // разбиваем таблицу по станциям
            Set<Station> stationsSet = new HashSet<>();
            String line = "";
            String suffix = "";
            for (Element row : rows) {
                Elements elements = row.getElementsByTag("a");
                Station station;
                String suffix2 = getSuffix(row); // находим номер линии для данной станции
                if (suffix.length() == 0) { // первичное задание линии-ключа
                    suffix = suffix2; // задаем номер линии
                    line = getLine(line, elements); // создаем название линии с номером
                }
                if (suffix.equals(suffix2)) { // если станция находится на прежней линии
                    station = getStation(row); // находим название станции
                } else { // если перешли на станцию новой линии
                    updateMap(setMap, line, stationsSet); // сбрасываем в карту результат парсинга предыдущей линии
                    stationsSet = new HashSet<>(); // обнуляем сет для записи станций новой линии
                    suffix = suffix2; // задаем номер новой линии
                    line = getLine(line, elements); // создаем название линии с номером
                    station = getStation(row); // находим название станции
                }
                if (station.getNameOfStation().length() > 0) {
                    stationsSet.add(station); // пройдя все элементы ряда, добавляем найденную стануию в сет
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLine(String line, Elements elements) {
        for (Element element : elements) {
            String title = element.attr("title");
            if (title.contains("линия") && title.split(" ").length > 1 && title.split(" ").length < 4) {
                line = title;
                break;
            }
        }
        return line;
    }

    public Station getStation(Element row) {
        String result = "";
        Elements elements = row.getElementsByTag("a");
        for (Element element : elements) {
            String title = element.attr("title");
            if (title.contains("станция метро")) {
                result = title.replaceFirst("\\(.*\\)", "").trim();
            }
        }
        Element el = row.getElementsByTag("span").first();
        String suffix = "";
        if (el != null) {
            if (el.html().length() > 0 && el.html().length() <= 2) {
                suffix = el.html();
            }
        }
        return new Station(result, suffix);
    }

    private static String getSuffix(Element row) {
        Element el = row.getElementsByTag("span").first();
        String suffix = "";
        if (el != null) {
            if (el.html().length() > 0 && el.html().length() <= 2) {
                suffix = el.html();
            }
        }
        return suffix;
    }

    private static void updateMap(Map<String, Set<Station>> setMap, String line, Set<Station> stations) {
        if (line.length() > 0) {
            setMap.put(line, stations);
        }
    }
}
