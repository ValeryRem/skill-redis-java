package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingHtml {
    String origin;
    String cssQuery;
    Elements elements;
    TreeSet<String> result = new TreeSet<>();

    public ParsingHtml(String origin, String cssQuery) {
        this.origin = origin;
        this.cssQuery = cssQuery;
        result.add(origin);
    }

    public TreeSet<String> getResult() {
        return result;
    }

    public void getHTMLinfo(String origin) {
        String prefix = "https://secure-headland-59304.herokuapp.com";
        String fullRef;
        try {
//            File htmlFile = new File(origin);
            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();
            elements = doc.body().getElementsByAttribute(cssQuery);
//            elements.forEach(x -> System.out.println(x.html()));
//            System.out.println("childNodeSize(): " + doc.childNodeSize());
//            List<Node> nodeList = doc.childNodes();
//            nodeList.forEach(x -> System.out.println(x.nodeName()));
            for (Element element : elements) {
                if (element.html().contains("child")) {
                    String htmlStr = element.toString();
                    String suffix = htmlStr.split("\"")[1];
                    if (!suffix.startsWith("/")) {
                        suffix = "/".concat(suffix);
                    }
                    fullRef = prefix.concat(suffix);
//                    System.out.println(fullRef);
                    if (!result.contains(fullRef)) {
                    result.add(fullRef);
                    origin = fullRef;
                    getHTMLinfo(origin);
                    }
                    if (elements.size() < 1) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (elements.size() > 0) {
//            String prefix =  "https://lenta.ru";
//                List<Element> list = elements.stream().distinct().collect(Collectors.toList());
//            for (Element el : list) {
//                String suffix = el.attr(cssQuery);
//                if (!suffix.startsWith("http")) {
//                    System.out.println(prefix.concat(suffix));
//                }
//            }
//        } else {
//            System.out.println("Result List is empty!");
//        }
    }
}

