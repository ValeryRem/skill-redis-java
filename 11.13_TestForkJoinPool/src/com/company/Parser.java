package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Parser extends RecursiveAction {

    private String url;
    private ResultStore resultStore;
    private ParserContext parserContext;

    private boolean shouldStop = false;

    public Parser(String url, ParserContext parserContext, ResultStore resultStore) {
        this.url = url.trim();
        this.parserContext = parserContext;
        this.resultStore = resultStore;
    }

    @Override
    protected void compute() {
        shouldStop = shouldStop();
        if (shouldStop) {
            try {
                ForkJoinPool.commonPool().shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        parseUrl();
    }

    private boolean shouldStop() {
        if (resultStore.getUrlAdded().size() > parserContext.getLimitOfResult()) {
            shouldStop = true;
        }
        return shouldStop;
    }

    private void parseUrl() {
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(url).maxBodySize(3_000_000).userAgent("Mozilla").get();
            elements = doc.select("a");
            for (Element element : elements) {
                processElement(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processElement(Element el) {
        String attr = el.attr("abs:href");
        if (attr.contains(parserContext.getPrefix()) && !attr.contains(".pdf") && !resultStore.getUrlAdded().contains(attr) && !attr.contains("#")
                && !attr.contains("@") && !attr.contains("tel:") && !shouldStop) {
            resultStore.getUrlAdded().add(attr);
            Parser parser = new Parser(attr, parserContext, resultStore);
            System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + resultStore.getUrlAdded().size());
            if (!resultStore.getTaskSet().contains(parser.url)) {
                resultStore.getTaskSet().add(parser.url);
                resultStore.getChildParsers().add(parser);
                parser.fork();
            }
        }
    }
}