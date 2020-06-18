package com.company;

import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingHtml {
    private final TreeSet<String> result = new TreeSet<>();
    private String prefix = "";
    private final int depthOfParsing;
    private final int limitOfResultList;

    public ParsingHtml(String prefix, int depthOfParsing, int limitOfResultList) {
        this.prefix = prefix;
        this.depthOfParsing = depthOfParsing;
        this.limitOfResultList = limitOfResultList;
        result.add(prefix + "/");
    }

    private String url = prefix + "/";
    private int index = 0;

    public TreeSet<String> getHTMLinfo(String url) {
        String fullRef;
        try {
            Document doc = Jsoup.connect(url).maxBodySize(3_000_000).get();
            Elements elements = doc.body().select("a");

            for (Element element : elements) {
                String suffix = element.attr("href");
                if (!suffix.startsWith("/")) {
                    suffix = "/".concat(suffix);
                }
                fullRef = prefix + suffix;
                if (!suffix.contains("http") && suffix.length() > 3 && !suffix.startsWith("/#") && !result.contains(fullRef) && !suffix.contains("@")
                && !suffix.contains("tel:+") && !suffix.endsWith(".pdf") && !suffix.contains("/tg:/")) {
                    index++;
                    System.out.println(suffix + " -> " + index);
                    while (result.size() < limitOfResultList) {
                        result.add(fullRef);
                        if (index == depthOfParsing || index == elements.size() - 1) {
                            index = 0;
                            continue;
                        }
                        url = fullRef;
                        getHTMLinfo(url);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
