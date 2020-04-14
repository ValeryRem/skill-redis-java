/**
 * Написать код, который будет получать список абсолютных путей изображений с главной страницы сайта lenta.ru
 * и скачивать эти изображения в заданную папку.
 */
package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        String filePath = "src/com/company/Images.txt";
        String dirPictures = "src/com/company/LentaPictures/";
        try (FileWriter writer = new FileWriter(filePath, true)){
            Document doc = Jsoup.connect("https://lenta.ru/").maxBodySize (1_000_000).get();
            Elements img = doc.getElementsByTag("img");
            int i = 0;
            for (Element el : img) {
                String src = el.absUrl("src");
                writer.append(src).append("\n");
                try (InputStream in = new URL(src).openStream()) {
                    Files.copy(in, Paths.get(dirPictures + i++ + ".png"), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
