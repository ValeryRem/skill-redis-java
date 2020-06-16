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
    private List<String> result = new ArrayList<>();
    private TreeSet<String> interResult = new TreeSet<>();
    private String prefix = "https://lenta.ru";

    public ParsingHtml(String origin, String cssQuery) {
        this.origin = origin;
        this.cssQuery = cssQuery;
    }

    public List<String> getHTMLinfo(String origin) {

//        String prefix = "https://secure-headland-59304.herokuapp.com";
        try {
            File htmlFile = new File(origin);
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            elements = doc.body().select("a");
//            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();

            for (Element element : elements) {
                String suffix = element.attr("href");
                if (!suffix.contains("https")) {
                    interResult.add(prefix + suffix); //Getting sorted & distinct collection
                    getHTMLinfo(prefix + suffix);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//                }
//                if (element.html().contains("child")) {
//                    String htmlStr = element.toString();
//                    String suffix = htmlStr.split("\"")[1];
//                    if (!suffix.startsWith("/")) {
//                        suffix = "/".concat(suffix);
        List<String> newList = new LinkedList<>(interResult); //Getting sorted & distinct list

        for (String s : newList) {
            s = getSpan(s) + s;
            result.add(s);
        }
        System.out.println("Result list size: " + result.size());
        return result;
    }

    private String getSpan(String str) {
        String zero = "";
        String span = "    ";
        int coeff = str.split("/").length - origin.split("/").length;
        for (int i = 0; i < coeff; i++) {
            zero += span ;
        }
        return zero;
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

