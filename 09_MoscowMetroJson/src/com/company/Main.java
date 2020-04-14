/**
 * Написать код парсинга страницы Википедии “Список станций Московского метрополитена”, на основе этой страницы
 * создавать JSON-файл со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json,
 * приложен к домашнему заданию)
 * Также пропарсить и вывести в JSON-файл переходы между станциями.
 * Написать код, который прочитает созданный JSON-файл и напечатает количества станций на каждой линии.
 */

package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
import java.nio.file.*;

public class Main {
    static String filePath = "src/com/company/text.txt";
    static String origin = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0#%D0%A1%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B8_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter(filePath, true)){
            Document doc = Jsoup.connect(origin).maxBodySize (500_000).get();
            Elements elements = doc.getElementsByTag("span");
            int i = 0;
            for (Element el : elements) {
                String title = el.absUrl("title");
                writer.append(title).append("\n");
                try (InputStream in = new URL(title).openStream()) {
                    Files.copy(in, Paths.get(filePath + i++ + ".txt"), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }    }
}
