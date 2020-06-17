package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String origin = "https://secure-headland-59304.herokuapp.com/";
    //       String url = "https://lenta.ru/";
    private  static final String cssQuery = "href";
//    private static final

    public static void main(String[] args) {
        long from = new Date().getTime();
//        htmlParsingResult(from);
        forkJoinResult(from);
    }

    private static void htmlParsingResult(long from) {
        ParsingHtml parsingHtml = new ParsingHtml(Main.origin, cssQuery);
        TreeSet<String> result = parsingHtml.getHTMLinfo(Main.origin);
        output(from, result);
    }

    private static void forkJoinResult(long from) {
        ParsingHtml parsingHtml = new ParsingHtml(Main.origin, cssQuery);
        TreeSet<String> result = parsingHtml.getHTMLinfo(Main.origin);
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(10, Main.origin, cssQuery, parsingHtml);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.invoke(myRecursiveAction);
        output(from, result);
    }

    private static void output(long from, TreeSet<String> result) {
        List<String> list = new ArrayList<>();
        for (String s : result) {
            String span = "";
            String shift = "    ";
            int coeff = s.split("/").length - origin.split("/").length;
            for (int i = 0; i < coeff; i++) {
                span += shift ;
            }
            list.add(span + s);
        }
        list.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (new Date().getTime() - from) / 1000 + " sec.");
    }
}
