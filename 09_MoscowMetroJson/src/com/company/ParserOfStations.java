package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.*;

public class ParserOfStations {

    List<Station> listIndex = new ArrayList<>();

    public ParserOfStations() {
    }

    public void parsingMetroMap(String origin, Map<String, List<String>> stationsMap, String cssQuery) {
        try {
//            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();
            File htmlFile = new File(origin);
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            Element table = doc.select(cssQuery).first();
            if (table != null) {
                Elements rows = table.getElementsByTag("tr"); // разбиваем таблицу по станциям
                List<String> stationsList = new ArrayList<>();
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
                        line = station.line;// + ". " + getLineName(row); // выводим название линии
                    } else { // если перешли на станцию новой линии
                        updateMap(stationsMap, line, stationsList); // сбрасываем в карту результат парсинга предыдущей линии
                        stationsList = new ArrayList<>(); // обнуляем список для записи станций новой линии
                        suffix = suffix2; // задаем номер новой линии
                        station = getStation(row); // находим название новой станции
                        line = station.line;// + ". " + getLineName(row); // выводим название новой линии
                        station.setName(station.name);
                    }
                    if (station.getName().length() > 3) {
                        stationsList.add(station.name); // пройдя все элементы ряда, добавляем найденную станцию в лист
                        listIndex.add(station);
                    }
                }
            } else {
                System.err.println("String cssQuery: " + cssQuery + " - does not work. Input new cssQuery!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String getLineName(Element row) {
//        Elements elements = row.getElementsByTag("a");
//        String name = "";
//        for (Element element : elements) {
//            String title = element.attr("title");
//            if (title.contains("линия") && title.split(" ").length > 1 && title.split(" ").length < 4) {
//                name = title;
//                break;
//            }
//        }
//        return name;
//    }

    public Station getStation(Element row) {
        String name = "";
        String line = "";
        Elements boxes = row.getElementsByTag("td");
        for (Element box : boxes) {
            if (box.html().contains("линия") || box.html().contains("Московское центральное кольцо")) {
                Element el = box.selectFirst("span");
                if (el != null) {
                    if (el.html().length() > 0 && el.html().length() <= 4) {
                        line = el.html();
                    }
                }
            }

            if (box.html().contains("станция метро") || box.html().contains("станция МЦК")) {
                Elements elements = box.getElementsByTag("a");
                for (Element element : elements) {
                    String title = element.attr("title");
                    if (title.contains("станция метро") || title.contains("станция МЦК")) {
                        name = title.replaceFirst("\\(.*\\)", "");
                    }
                }
            }
        }
        return
                new Station(name, line, getNamesOfConnectedStations(row));
    }

    private String getSuffix(Element row) {
        Element el = row.getElementsByTag("span").first();
        String suffix = "";
        if (el != null) {
            if (el.html().length() > 0 && el.html().length() <= 2) {
                suffix = el.html();
            }
        }
        return suffix;
    }

    private void updateMap(Map<String, List<String>> listMap, String line, List<String> stations) {
        if (line.length() > 0) {
            listMap.put(line, stations);
        }
    }

    private List<String> getNamesOfConnectedStations(Element row) {
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

    public List<Station> getListIndex() {
        return listIndex;
    }
}
