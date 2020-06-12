package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingHtml {
    String origin;
    String cssQuery;
    Elements elements;

    public ParsingHtml(String origin, String cssQuery) {
        this.origin = origin;
        this.cssQuery = cssQuery;
        getHTMLinfo();
    }

    public void getHTMLinfo() {
//        List<Element> result = new ArrayList<>();
        try {
            File htmlFile = new File(origin);
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            elements = doc.body().getElementsByAttribute(cssQuery);//.getElementsByTag(cssQuery);
//             elements = doc.select(cssQuery);
//            result.addAll(elements);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (elements.size() > 0) {
            String prefix =  "https://lenta.ru";
            for (Element el : elements) {
                String suffix = el.attr("href");
                if (!suffix.startsWith("http")) {
                    System.out.println(prefix.concat(suffix));
                }
            }
        } else {
            System.out.println("Result List is empty!");
        }
    }
}
