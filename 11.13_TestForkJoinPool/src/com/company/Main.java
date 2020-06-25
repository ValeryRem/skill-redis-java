package com.company;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String prefix = "https://skillbox.ru"; //"https://secure-headland-59304.herokuapp.com"; // "https://lenta.ru"; //
    private static final String url = prefix + "/";
    private final static int LIMIT_OF_RESULT = 100;
    private static Set<String> urlAdded = new HashSet<>();

    public static void main(String[] args) {
        long from = System.currentTimeMillis();
        Parser parser = new Parser(from, url, prefix, urlAdded, LIMIT_OF_RESULT);
        ForkJoinPool.commonPool().invoke(parser); //new ForkJoinPool().invoke(parser);//
        try {
            parser.output(from, urlAdded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
