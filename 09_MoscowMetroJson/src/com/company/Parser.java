package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.TreeSet;

public class Parser {
    public Parser() {
    }

    public void parsMetroLines(String origin, Map<String, TreeSet<String>> setMap) {
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(2_500_000).get();
            Element table = doc.select("table:has(a[title='Бульвар Рокоссовского (станция метро)'])").first();
            Elements rows = table.select("tr"); // разбиваем таблицу по станциям
            TreeSet<String> stationsSet = new TreeSet<>();
            String line = "";
            String suffix = "";
            for (Element row : rows) {
                Elements elements = row.getElementsByTag("a");
                String station;
                String suffix2 = getSuffix(row); // находим номер линии для данной станции
                if (suffix.length() == 0) { // первичное задание линии-ключа
                    suffix = suffix2; // задаем номер линии
                    line = getLine(line, suffix, elements); // создаем название линии с номером
                }
                if (suffix.equals(suffix2)) { // если станция находится на прежней линии
                    station = getStation(elements); // находим название станции
                } else { // если перешли на станцию новой линии
                    updateMap(setMap, line, stationsSet); // сбрасываем в карту результат парсинга предыдущей линии
                    stationsSet = new TreeSet<>(); // обнуляем сет для записи станций новой линии
                    suffix = suffix2; // задаем номер новой линии
                    line = getLine(line, suffix, elements); // создаем название линии с номером
                    station = getStation(elements); // находим название станции
                }
                if (station.length() > 0) {
                    stationsSet.add(station); // пройдя все элементы ряда, добавляем найденную стануию в сет
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getLine(String line, String suffix, Elements elements) {
        for (Element element : elements) {
            String title = element.attr("title");
            if (title.contains("линия") && title.split(" ").length > 1 && title.split(" ").length < 4) {
                line = String.format("\n%s (%s)", title, suffix);
                break;
            }
        }
        return line;
    }

    private static String getStation(Elements elements) {
        String result = "";
        for (Element element : elements) {
            String title = element.attr("title");
            if (title.contains("станция метро")) {
                result = "\t\n" + title.replaceFirst("\\(.*\\)", "").trim();
            }
        }
        return result;
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

    private static void updateMap(Map<String, TreeSet<String>> setMap, String line, TreeSet<String> stations) {
        if (line.length() > 0) {
            setMap.put(line, stations);
        }
    }
}
