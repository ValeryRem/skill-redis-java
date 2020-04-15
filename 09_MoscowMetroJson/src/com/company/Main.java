/**
 * 1) Написать код парсинга страницы Википедии “Список станций Московского метрополитена”, который будет
 * создавать JSON-файл со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json,
 * приложен к домашнему заданию)
 * Также пропарсить и вывести в JSON-файл переходы между станциями.
 * 2) Написать код, который прочитает созданный JSON-файл и напечатает количества станций на каждой линии.
 */

package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String origin = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9" +
                "_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1" +
                "%82%D0%B5%D0%BD%D0%B0#%D0%A1%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B8_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_" +
                "%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
        Map<String, Set<String>> listMap = new HashMap<>();
        parsMetroLines(origin, listMap);
        listMap.entrySet().forEach(System.out::println);
        System.out.println("\nNumber of lines: " + listMap.size());
    }

    private static void parsMetroLines (String origin, Map<String, Set<String>> listMap) {
        Document doc = null;
        try {
            doc = Jsoup.connect(origin).maxBodySize(2_000_000).get();
            Elements rows = doc.select("tr");
            for (Element row : rows) {
                Elements elements = row.getElementsByTag("a");
                Set<String> hashSet = new HashSet<>();
                String key = "";
                for (Element element : elements) {
                    String title = element.attr("title");
                    if (key.isEmpty() && title.contains("линия") && title.split(" ").length > 1 && title.split(" ").length < 4) {
                        key = title;
                    }
                    String value = "";
                    if (title.contains("станция метро")) {
                        value = title.replaceFirst("\\(.*\\)", "").trim();
                    }
                    if (title.contains("Переход на станцию")) {
                        value = "\t* " + title;
                    }
                    if (listMap.containsKey(key)) {
                        listMap.get(key).add("\n\t\t" + value);
                    } else  {
                        if (key.length() > 0 && value.length() > 0) {
                            hashSet.add("\n\t\t" + value);
                            listMap.put(key, hashSet);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    private void jsonCreator (Map<String, Set<String>> map) {
//        JSONObject json = new JSONObject();
//        json.putAll( map );
//    }
}
