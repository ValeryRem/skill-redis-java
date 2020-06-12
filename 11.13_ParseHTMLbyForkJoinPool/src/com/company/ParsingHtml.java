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

    public ParsingHtml(String origin, String cssQuery) {
        this.origin = origin;
        this.cssQuery = cssQuery;
    }

    public void getHTMLinfo() {
        List<Element> result = new ArrayList<>();
        try {
            File htmlFile = new File(origin);
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            result = doc.body().getElementsByTag(cssQuery);
//            Elements elements = doc.select(cssQuery);
//            result.addAll(elements);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.size() > 0) {
            for (Element el : result) {
                System.out.println(el.html());
            }
        } else {
            System.out.println("Result List is empty!");
        }
    }
}
