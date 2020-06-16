package com.company;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingHtml {
    private String origin;
    private String cssQuery;
    private Elements elements;
    private List<String> result = new LinkedList<>();

    public ParsingHtml(String origin, String cssQuery) {
        this.origin = origin;
        this.cssQuery = cssQuery;
        result.add(origin);
    }

    public List<String> getHTMLinfo(String origin) {
        String prefix = "https://secure-headland-59304.herokuapp.com";
        String fullRef;
        String span = "    ";
        try {
//            File htmlFile = new File(origin);
            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();
            elements = doc.body().getElementsByAttribute(cssQuery);
            String newPrefix;
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
                    int coeff = suffix.split("/").length - 1;
                    System.out.println("size of span: " + coeff);
                    newPrefix = "";
                    for (int i = 0; i < coeff; i++) {
                        newPrefix += span ;
                    }
                    fullRef = newPrefix + prefix + suffix;
//                    System.out.println(fullRef);
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

