package com.company;

public class Main {

    public static void main(String[] args) {
//            String url = "https://lenta.ru/";
            String origin = "src/resources/lenta.html";
            String cssQuery = "a href";
            ParsingHtml parsingHtml = new ParsingHtml(origin, cssQuery);
            parsingHtml.getHTMLinfo();
        }
}
