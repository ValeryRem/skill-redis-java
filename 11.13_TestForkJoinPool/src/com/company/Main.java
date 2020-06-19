package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String prefix = "https://secure-headland-59304.herokuapp.com"; //"https://lenta.ru"; //"https://skillbox.ru"; //
    private static final String url = prefix + "/";
    private static MyRecursiveAction myRecursiveAction;
    private static TreeSet<String> result;

    public static void main(String[] args) {
        long from = new Date().getTime();
        try {
            Document doc = Jsoup.connect(url).maxBodySize(3_000_000).get();
            Elements elements = doc.body().getElementsByAttribute("href");
            myRecursiveAction = new MyRecursiveAction(prefix, elements, 0, elements.size());
            forkJoinResult(from);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void htmlParsingResult(long from) {
//        TreeSet<String> result = myRecursiveAction.getResult();
//        try {
//            output(from, result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void forkJoinResult(long from) {
        result = myRecursiveAction.startForkJoin();
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
