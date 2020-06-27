package com.company;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String prefix = "https://skillbox.ru"; //"https://secure-headland-59304.herokuapp.com"; // "https://lenta.ru"; //
    private static final String url = prefix + "/";
    private final static int LIMIT_OF_RESULT = 300;
    private static Set<String> urlAdded = new HashSet<>();
    private static Set<Parser> childParsers = new HashSet<>();
    private static Set<String> taskSet = new HashSet<>();

    public static void main(String[] args) {
        long from = System.currentTimeMillis();
        ParserContext parserContext = new ParserContext(from, prefix, LIMIT_OF_RESULT);
        ResultStore resultStore = new ResultStore(childParsers, urlAdded, taskSet);
        Parser parser = new Parser(url, parserContext, resultStore);
        Presenter presenter = new Presenter(parser, resultStore, parserContext);

        ForkJoinPool.commonPool().invoke(parser); //new ForkJoinPool().invoke(parser);//

        try {
            ForkJoinPool.commonPool().awaitTermination(1, TimeUnit.MINUTES);
            presenter.output(from, resultStore.getUrlAdded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
