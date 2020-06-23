package com.company;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String prefix =  "https://skillbox.ru"; //"https://secure-headland-59304.herokuapp.com"; //"https://lenta.ru"; //
    private static final String url = prefix + "/";
    private static Set<String> urlAdded = new HashSet<>();

    public static void main(String[] args) {
        long from = System.currentTimeMillis();//new Date().getTime();
        Parser parser = new Parser(from, url, prefix, urlAdded);
        ResultStore resultStore = new ResultStore(url);
        ForkJoinPool.commonPool().invoke(parser); //new ForkJoinPool().invoke(parser);//
        try {
            resultStore.output(from, urlAdded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
