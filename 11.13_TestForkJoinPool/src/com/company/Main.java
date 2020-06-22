package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String prefix = "https://secure-headland-59304.herokuapp.com"; //"https://skillbox.ru"; //"https://lenta.ru"; //
    private static final String url = prefix + "/";
    private static ResultStore resultStore = new ResultStore();
    private static Parser parser = new Parser(url, prefix);

    public static void main(String[] args) {
        long from = System.currentTimeMillis();//new Date().getTime();
            Set<String> siteMap = ForkJoinPool.commonPool().invoke(parser); //new ForkJoinPool().invoke(parser);//
        try {
            output(from, siteMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void output(long from, Set<String> set) throws IOException {
        Set<String> result = new TreeSet<>(set);
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
        System.out.println("\nResult:");
        list.forEach(System.out::println);
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (System.currentTimeMillis() - from) / 1000  + " sec.");
    }
}
