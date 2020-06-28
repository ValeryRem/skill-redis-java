package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Parser extends RecursiveAction {

    private final String url;
    private final ResultStore resultStore;

    public Parser(String url, ResultStore resultStore) {
        this.url = url.trim();
        this.resultStore = resultStore;
    }

    @Override
    protected void compute() {
        if (shouldStop()) {
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
        return
                resultStore.getUrlAdded().size() > resultStore.getLimitOfResult();
    }

    private void parseUrl() {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(3_000_000).userAgent("Mozilla").get();
            Elements elements = doc.select("a");
            for (Element element : elements) {
                processElement(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processElement(Element el) {
        String attr = el.attr("abs:href");
        if (attr.contains(resultStore.getPrefix()) && !attr.contains(".pdf") && !resultStore.getUrlAdded().contains(attr) && !attr.contains("#")
                && !attr.contains("@") && !attr.contains("tel:") && !shouldStop()) {
            Parser parser = new Parser(attr, resultStore);
            System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + resultStore.getUrlAdded().size());
            if (!resultStore.getUrlAdded().contains(parser.url)) {
                resultStore.getUrlAdded().add(parser.url);
                resultStore.getChildParsers().add(parser);
                parser.fork();
            }
        }
    }
}