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
    List <Line> lineList = new ArrayList<>();;
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
                String lineNumber = "";
                String primeLineNumber = "";
                Line line;
                for (Element row : rows) {
                    Station station;
                    String lineNumberNew = getLineNumber(row); // находим номер линии для данной станции
                    String lineName = getLineName(row);
                    if (primeLineNumber.length() == 0) { // первичное задание линии-ключа
                        primeLineNumber = lineNumberNew; // задаем номер линии
                    }
                    if (primeLineNumber.equals(lineNumberNew)) { // если станция находится на прежней линии
                        station = getStation(row); // находим название станции
                        lineNumber = station.lineNumber; // выводим название линии
                    } else { // если перешли на станцию новой линии
                        line = new Line(lineNumberNew, lineName);
                        lineList.add(line);
                        updateMap(stationsMap, lineNumber, stationsList); // сбрасываем в карту результат парсинга предыдущей линии
                        stationsList = new ArrayList<>(); // обнуляем список для записи станций новой линии
                        station = getStation(row); // находим название новой станции
                        lineNumber = station.lineNumber;// выводим название новой линии
                        primeLineNumber = lineNumberNew; // задаем номер новой линии
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
        Comparator<Line> comparator = Comparator.comparing(ele -> ele.lineNumber);
        Set<Line> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(lineList);
        lineList = new ArrayList<>(treeSet);
        List <Line> lines = lineList.stream().filter(x -> x.lineNumber.length() > 1 && x.lineName.length() > 3).collect(Collectors.toList());

//    List <Line> lines = lineList.stream()
//    .filter(x -> x.lineName.length() > 3 && x.lineNumber.length() > 1)
//    .distinct()
//    .collect(Collectors.toList());
        return
                new StationIndex(stationsMap, lines, connections);
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
            }
            if (name.length() > 0 && line.length() > 0) {
                break;
            }
        }
        return
                new Station(line, name);
    }

    private String getLineNumber(Element row) {
        String lineNumber = "";
        Element el = row.getElementsByTag("span").first();
        if (el != null) {
            if (el.html().length() > 0 && el.html().length() <= 4) {
                lineNumber = el.html();
            }
        }
        return lineNumber;
    }

    private String getLineName (Element row) {
        String lineName = "";
        Elements boxes = row.getElementsByTag("a");
        lineName = boxes.stream()
                .filter(x -> x.html().contains("линия") || x.html().contains("Московское центральное кольцо") || x.html().contains("Московский монорельс"))
                .map(x -> x.attr("title")).limit(1)
                .collect(Collectors.joining());
        return lineName;
    }

    private void updateMap (Map<String, List<String>> listMap, String line, List<String> stations) {
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
        List<Station> transferHubs = new ArrayList<>();
        Elements elements = row.getElementsByTag("td");
        for (Element td: elements) {
            if (td.html().contains("Переход на станцию")) {
                Elements lines = td.select("span[class='sortkey']");
                Elements names = td.select("span[title]");
                for (int i = 0; i < lines.size(); i++) {
                    for (Station st : listIndex) {
                        if (names.get(i).html().contains(st.name) && lines.get(i).html().equals(st.lineNumber)) {
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
