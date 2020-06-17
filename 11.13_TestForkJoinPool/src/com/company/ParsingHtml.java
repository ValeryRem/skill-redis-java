package com.company;

import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingHtml {
    private final String cssQuery;
    private TreeSet<String> result = new TreeSet<>();

    public ParsingHtml(String origin, String cssQuery) {
        this.cssQuery = cssQuery;
        result.add(origin);
    }

    public TreeSet<String> getResult() {
        return result;
    }
    int k = 0;
    public TreeSet<String> getHTMLinfo(String origin) {
        String prefix = "https://secure-headland-59304.herokuapp.com";
        String fullRef;
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();
            Elements elements = doc.body().getElementsByAttribute(cssQuery);
            for (Element element : elements) {
                if (element.html().contains("child")) {
                    System.out.println(k++);
                    String htmlStr = element.toString();
                    String suffix = htmlStr.split("\"")[1];
                    if (!suffix.startsWith("/")) {
                        suffix = "/".concat(suffix);
                    }
                    fullRef = prefix + suffix;
                    if (!result.contains(fullRef)) {
                        result.add(fullRef);
                        origin = fullRef;
                        getHTMLinfo(origin);
                    }
                    if (elements.size() < 1) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
