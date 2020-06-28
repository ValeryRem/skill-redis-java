package com.company;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String prefix = "https://skillbox.ru"; //"https://secure-headland-59304.herokuapp.com"; //"https://lenta.ru"; //
    private static final String url = prefix + "/";
    private final static int LIMIT_OF_RESULT = 300;
    private static final Set<Parser> childParsers = new HashSet<>();
    private static final Set<String> urlAdded = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        long from = System.currentTimeMillis();
        ResultStore resultStore = new ResultStore(childParsers, urlAdded, prefix, LIMIT_OF_RESULT);
        Parser parser = new Parser(url, resultStore);
        Presenter presenter = new Presenter(resultStore);

        ForkJoinPool.commonPool().invoke(parser); //new ForkJoinPool().invoke(parser);//
        try {
            ForkJoinPool.commonPool().awaitTermination(1, TimeUnit.MINUTES);
            presenter.output(from, resultStore.getUrlAdded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
