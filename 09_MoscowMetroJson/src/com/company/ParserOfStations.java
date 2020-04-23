package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ParserOfStations {

    List<Station> listIndex = new ArrayList<>();
    List<Connection> connections = new ArrayList<>();
    Connection connection = new Connection();

    public ParserOfStations() {
    }

    public StationIndex parsingMetroMap(String origin, String cssQuery) {
        Map<String, List<String>> stationsMap = new TreeMap<>();
        try {
//            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get(); // вариант загрузки из сети
            File htmlFile = new File(origin);
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            Elements tables = doc.select(cssQuery);//.first();
            if (tables != null) {
                Elements rows = tables.select("tr");//.getElementsByTag("tr"); // разбиваем таблицу по станциям
                List<String> stationsList = new ArrayList<>();
                String line = "";
                String suffix = "";
                for (Element row : rows) {
                    Station station;
                    String suffix2 = getSuffix(row); // находим номер линии для данной станции

                    if (suffix.length() == 0) { // первичное задание линии-ключа
                        suffix = suffix2; // задаем номер линии
                    }
                    if (suffix.equals(suffix2)) { // если станция находится на прежней линии
                        station = getStation(row); // находим название станции
                        line = station.line; // выводим название линии
                    } else { // если перешли на станцию новой линии
                        updateMap(stationsMap, line, stationsList); // сбрасываем в карту результат парсинга предыдущей линии
                        stationsList = new ArrayList<>(); // обнуляем список для записи станций новой линии
                        suffix = suffix2; // задаем номер новой линии
                        station = getStation(row); // находим название новой станции
                        line = station.line;// выводим название новой линии
                    }
                    if (station.name.length() > 3 && !stationsList.contains(station.name)) {
                        stationsList.add(station.name); // пройдя все элементы ряда, добавляем найденную станцию в лист
                        listIndex.add(station);
                        if (row.html().contains("Переход на станцию") && !getTransferStations(row).isEmpty()) {
                            connections.add(new Connection(station, getTransferStations(row)));
                        }
                    }
                }
            } else {
                System.err.println("String cssQuery: " + cssQuery + " - does not work. Input new cssQuery!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return
                new StationIndex(stationsMap, connections);
    }

    public Station getStation(Element row) {
        String name = "";
        String line = "";
        Elements boxes = row.getElementsByTag("td");
        for (Element box : boxes) {
            if (box.html().contains("линия") || box.html().contains("Московское центральное кольцо") ||
                    box.html().contains("Московский монорельс")) {
                Element el = box.selectFirst("span");
                if (el != null) {
                    if (el.html().length() > 0 && el.html().length() <= 4) {
                        line = el.html();
                    }
                }
            }
            if (box.html().contains("станция")) {
                Elements elements = box.getElementsByTag("a");
                name = elements.stream()
                        .map(x -> x.attr("title"))
                        .filter(x -> x.contains("станция"))
                        .map(x -> x.replaceFirst("\\(.*\\)", ""))
                        .collect(Collectors.joining());
//                for (Element element: elements) {
//                    String title = element.attr("title");
//                    if (title.contains("станция")) {
//                        name = title.replaceFirst("\\(.*\\)", "");
//                    }
//                }
            }
            if (name.length() > 0 && line.length() > 0) {
                break;
            }
        }
        return
                new Station(line, name);
    }

//        String name = "";
//        String line = "";
//        Elements tds = row.getElementsByTag("td");
//        Elements boxes = tds.select("a[title]:contains('линия')");
//
////        Elements boxes = row.getElementsByTag("td").stream()
////                .filter(t -> t.html().contains("линия") || t.html().contains("Московское центральное кольцо"))
////                .collect(Collectors.toCollection(Elements::new));                        //row.getElementsByTag("td");
//        for (Element td : boxes) {
//            Element el = td.selectFirst("span");
//            if (el != null) {
//                if (el.html().length() > 0 && el.html().length() <= 4) {
//                    line = el.html();
//                }
//            }

//            Elements elements = td.getElementsByTag("a");
////            name = elements.stream()
////                    .map(t -> t.attr("title")).filter(t -> (t.contains("станция метро") || t.contains("станция МЦК")))
////                    .map(t -> t.replaceFirst("\\(.*\\)", "")).toString();
//            for (Element element : elements) {
//                String title = element.attr("title");
//                if (title.contains("станция метро") || title.contains("станция МЦК")) {
//                    String s = title.replaceFirst("\\(.*\\)", "");
//                    if (s.length() > 3) {
//                        name = s;
//                        break;
//                    }
//                }
//            }
//        }
//       return new Station(name, line);
//    }
    private String getSuffix(Element row) {
        String suffix = "";
            Element el = row.getElementsByTag("span").first();
            if (el != null) {
                if (el.html().length() > 0 && el.html().length() <= 4) {
                    suffix = el.html();
                }
            }
        return suffix;
    }

    private void updateMap(Map<String, List<String>> listMap, String line, List<String> stations) {
        if (line.length() > 0 && stations.size() > 0) {
            if (listMap.containsKey(line)) {
                List<String> names = listMap.get(line);
                if (!stations.isEmpty() && !listMap.get(line).contains(stations)) {
                    names.addAll(stations);
                    names = names.stream().distinct().collect(Collectors.toList());
                }
                listMap.put(line, names);
            } else {
                listMap.put(line, stations);
            }
        }
    }

    public List<Station> getTransferStations(Element row) {
//        final String[] lineNumber = new String[1];
//        return
//                row.select("td").select("span").stream()
//                .map(x -> {
//                    lineNumber[0] = row.select("td:contains(Переход на станцию)").attr("span[class='sortkey']");
//                    return x.attr("title");
//                })
//                .filter(x -> x.contains("Переход на станцию"))
//                .map(x -> x.replaceAll("Переход на станцию", ""))
//                .map(x -> {
//                    Station s = null;
//                    for (Station st : listIndex) {
//                        if (x.contains(st.name) && st.line.equals(lineNumber[0])) {
//                            s = st;
//                            break;
//                        }
//                    }
//                    return s;
//                })
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
        List<Station> transferHubs = new ArrayList<>();
        Elements elements = row.getElementsByTag("td");
        for (Element td: elements) {
            if (td.html().contains("Переход на станцию")) {
                Elements lines = td.select("span[class='sortkey']");
                Elements names = td.select("span[title]");
                for (int i = 0; i < lines.size(); i++) {
                    for (Station st : listIndex) {
                        if (names.get(i).html().contains(st.name) && lines.get(i).html().equals(st.line)) {
                            transferHubs.add(st);
                            break;
                        }
                    }
                }
            }
        }
        return transferHubs;
    }
}
