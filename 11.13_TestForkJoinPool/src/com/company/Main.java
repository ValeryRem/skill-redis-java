package com.company;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String prefix = "https://skillbox.ru"; //"https://secure-headland-59304.herokuapp.com"; //"https://lenta.ru"; //
    private static final String url = prefix + "/";
    private final static int LIMIT_OF_RESULT = 500;
    private static Set<String> urlAdded = new HashSet<>();

    public static void main(String[] args) {
        long from = System.currentTimeMillis();
        ParserContext parserContext = new ParserContext(from, prefix, LIMIT_OF_RESULT);
        Parser parser = new Parser(url, urlAdded, parserContext);
        ForkJoinPool.commonPool().invoke(parser); //new ForkJoinPool().invoke(parser);//
    }
}
