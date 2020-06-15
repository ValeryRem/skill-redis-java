package com.company;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {
    private static String origin = "https://secure-headland-59304.herokuapp.com/";
    //       String url = "https://lenta.ru/";
    private  static String cssQuery = "href";
    private static ParsingHtml parsingHtml = new ParsingHtml(origin, cssQuery);

    public static void main(String[] args) {
       long from = new Date().getTime();
//        htmlParsingResult(from, origin, cssQuery);
        forkJoinResult(from, origin, cssQuery);
    }

    private static void htmlParsingResult(long from, String origin, String cssQuery) {
        parsingHtml.getHTMLinfo(origin);
        TreeSet<String> result = parsingHtml.getResult();
        result.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (new Date().getTime() - from) + " millisec.");
    }

    private static void forkJoinResult(long from, String origin, String cssQuery) {
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(10, origin, cssQuery);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);    forkJoinPool.invoke(myRecursiveAction);
        TreeSet<String> result = parsingHtml.getResult();
        result.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (new Date().getTime() - from) + " millisec.");
    }
}
