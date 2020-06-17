package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String prefix = "https://lenta.ru";//"https://secure-headland-59304.herokuapp.com";
    private static final String url = prefix + "/";
    private static final int depthOfParsing = 30;
    private static final int limitOfResultList = 500;

    private static final ParsingHtml parsingHtml = new ParsingHtml(prefix, depthOfParsing, limitOfResultList);

    public static void main(String[] args) {
        long from = new Date().getTime();
//        htmlParsingResult(from);
        forkJoinResult(from);
    }

    private static void htmlParsingResult(long from) {
        TreeSet<String> result = parsingHtml.getHTMLinfo(prefix);
        try {
            output(from, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void forkJoinResult(long from) {
        TreeSet<String> result = parsingHtml.getHTMLinfo(Main.url);
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(10, Main.url, parsingHtml);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.invoke(myRecursiveAction);
        try {
            output(from, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void output(long from, TreeSet<String> result) throws IOException {
        List<String> list = new ArrayList<>();
        FileWriter writer = new FileWriter("src\\com\\company\\output.txt");
        for (String s : result) {
            String span = "";
            String shift = "    ";
            int coeff = s.split("/").length - url.split("/").length;
            for (int i = 0; i < coeff; i++) {
                span += shift ;
            }
            list.add(span + s);
        }
        for(String str: list) {
            try {
                writer.write(str + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        list.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (new Date().getTime() - from) / 1000 + " sec.");
    }
}
