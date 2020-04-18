package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class ParserOfStations {

    List<Station> list = new ArrayList<>();

    public ParserOfStations() {
    }

    public void parsingMetroMap(String origin, Map<String, Set<Station>> setMap) {
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(2_500_000).get();
            Element table = doc.select("table:has(a[title='Сокольническая линия'])").first();
            Elements rows = table.getElementsByTag("tr"); // разбиваем таблицу по станциям
            Set<Station> stationsSet = new HashSet<>();
            String line = "";
            String suffix = "";
            for (Element row : rows) {
//                Elements elements = row.getElementsByTag("a");
                Station station;
                String suffix2 = getSuffix(row); // находим номер линии для данной станции

                if (suffix.length() == 0) { // первичное задание линии-ключа
                    suffix = suffix2; // задаем номер линии
                }
                if (suffix.equals(suffix2)) { // если станция находится на прежней линии
                    station = getStation(row); // находим название станции
                    station.setName(station.getName()); // добаляем к названию станции ее порядковый номер на линии
                    line = station.line + ". " + getLineName(row); // выводим название линии
                } else { // если перешли на станцию новой линии
                    updateMap(setMap, line, stationsSet); // сбрасываем в карту результат парсинга предыдущей линии
                    stationsSet = new HashSet<>(); // обнуляем сет для записи станций новой линии
                    suffix = suffix2; // задаем номер новой линии
                    station = getStation(row); // находим название новой станции
                    line = station.line + ". " + getLineName(row); // выводим название новой линии
                    station.setName(station.getName());
                }
                if (station.getName().length() > 3) {
                    stationsSet.add(station); // пройдя все элементы ряда, добавляем найденную стануию в сет
                    list.add(station);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLineName(Element row) {
        Elements elements = row.getElementsByTag("a");
        String name = "";
        for (Element element : elements) {
            String title = element.attr("title");
            if (title.contains("линия") && title.split(" ").length > 1 && title.split(" ").length < 4) {
                name = title;
                break;
            }
        }
        return name;
    }

    public Station getStation(Element row) {
        String name = "";
        Elements boxes = row.getElementsByTag("td");
        for (Element box : boxes) {
            if (box.html().contains("станция метро")) {
                Elements elements = box.getElementsByTag("a");
                for (Element element : elements) {
                    String title = element.attr("title");
                    if (title.contains("станция метро")) {
                        name = title.replaceFirst("\\(.*\\)", "");
                    }
                }
            }
        }
        Element el = row.getElementsByTag("span").first();
        String lineOwn = "";
        if (el != null) {
            if (el.html().length() > 0 && el.html().length() <= 2) {
                lineOwn = el.html();
            }
        }
        return new Station(name, lineOwn, getNamesOfConnectedStations(row));
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

    private static List<String> getNamesOfConnectedStations(Element row) {
        if (row.html().contains("Переход на станцию")) {
            Elements elements = row.getElementsByTag("td");
            List<String> result = new ArrayList<>();
            for (Element element : elements) {
                if (element.html().contains("Переход на станцию")) {
                    Elements items = element.getElementsByTag("a");
                    for (Element number : items) {
                        String st = number.attr("title");
                        if (st.contains("Переход на станцию")) {
                            result.add(st.replaceAll("Переход на станцию", ""));
                        }
                    }
                }
            }
            return result;
        } else {
            return null;
        }
    }

    public List<Station> getList() {
        return list;
    }
}
