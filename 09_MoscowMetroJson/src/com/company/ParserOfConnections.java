package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class ParserOfConnections {
    public ParserOfConnections () {

    }
    ParserOfStations parserOfStations = new ParserOfStations ();

    public void parsingConnections(String origin, Map<String, Set<Connection>> map) {
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(2_500_000).get();
            Element table = doc.select("table:has(a[title='Бульвар Рокоссовского (станция метро)'])").first();
            Elements rows = table.select("tr"); // разбиваем таблицу по станциям на ряды
            Set<Connection> set = new HashSet<>();
            for (Element row : rows) {
                String nameStation = getStation(row);
                String lineToConnect = getLineNumberToConnect(row);
                String connectionName = getConnectionName(row);
                Connection connection = new Connection(nameStation, lineToConnect, connectionName);
                if (nameStation.length() > 0){// && lineToConnect.length() > 0 && connectionName.length() > 0) {
                    set.add(connection);
                }
            }
            map.put("Connections", set);
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public String getStation(Element row) {
        String result = "";
        Elements elements = row.getElementsByTag("a");
        for (Element element : elements) {
            String nameStation = element.attr("title");
            if (row.html().contains("Переход на станцию") && nameStation.contains("станция метро")) {
                result = nameStation.replaceFirst("\\(.*\\)", "");
            }
        }
        return result;
    }

    public String getConnectionName(Element row) {
        Elements elements = row.getElementsByTag("span");
        String result = "";
        for (Element element: elements) {
            String connectionName = element.attr("title");
            if (connectionName.contains("Переход на станцию")) {
                result += connectionName + "; ";
            }
        }
        return result;
    }

    private static String getLineNumberToConnect(Element row) {
        Element el = row.getElementsByTag("span").first();
        String number = "";
        if (el != null) {
            if (el.html().length() > 0 && el.html().length() <= 2) {
                number = el.html();
            }
        }
        return number;
    }
}
