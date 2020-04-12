/**
 * Написать код, который будет получать список абсолютных путей изображений с главной страницы сайта lenta.ru
 * и скачивать эти изображения в заданную папку.
 */
package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://lenta.ru/").get();
            Elements img = doc.getElementsByTag("img");
            String filePath = "C:\\Users\\valery\\Desktop\\java_basics\\09_ParsingHTML\\src\\com\\company\\Images.txt";
            FileWriter writer = new FileWriter(filePath, true);

            for (Element el : img) {
                String src = el.absUrl("src") + "\n";
                Files.write(Paths.get(filePath), src.getBytes(), StandardOpenOption.APPEND);
                writer.append(src);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
