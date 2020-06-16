package com.company;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
//    private static final String origin = "https://secure-headland-59304.herokuapp.com/";
    private static final String origin = "src/resources/lenta.html"; //"https://lenta.ru/";
    //       String url = "https://lenta.ru/";
//    private  static final String cssQuery = "href";
  private  static final String cssQuery = "a[href]";
    private static final ParsingHtml parsingHtml = new ParsingHtml(origin, cssQuery);

    public static void main(String[] args) {
       long from = new Date().getTime();
//        htmlParsingResult(from);
        forkJoinResult(from);
    }

    private static void htmlParsingResult(long from) {
        parsingHtml.getHTMLinfo(Main.origin);
        List<String> result = parsingHtml.getHTMLinfo(Main.origin);
        result.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (new Date().getTime() - from) + " millisec.");
    }

    private static  void forkJoinResult(long from) {
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(10, Main.origin, Main.cssQuery);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);    forkJoinPool.invoke(myRecursiveAction);
        List<String> result = parsingHtml.getHTMLinfo(Main.origin);
        result.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (new Date().getTime() - from) + " millisec.");
    }
}
