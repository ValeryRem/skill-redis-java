package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.RecursiveTask;

public class Parser extends RecursiveTask<Set<String>> {

    private String url;
    private String prefix;
    private ResultStore resultStore = new ResultStore();
    private Set<Parser> childParsers = new HashSet<>();

    public Parser(String url, String prefix) {
        this.url = url.trim();
        this.prefix = prefix;
    }

    @Override
    protected Set<String> compute() {
        System.out.println(Thread.currentThread().getName() + " -> " + url);
        parseAndGetTasksForChilds();
        Set<Parser> subTaskSet = childParsers;
        for (Parser task : subTaskSet) {
                resultStore.getResult().addAll(task.join());
        }


        return resultStore.getResult();
    }

    private Set<Parser> parseAndGetTasksForChilds() {
        // здесь загружаем и парсим this.url и создаем массив задач для каждой дочерней ссылки.
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(url).maxBodySize(3_000_000).userAgent("Mozilla").get();
            elements = doc.select("a");  //getElementsByAttribute("href");//
            for (Element element : elements) {
                getUrlForTestSite(element);
            }
            // создаем Parser для каждой ссылки:
            resultStore.getResult().forEach(x -> {
                Parser parser = new Parser(x, prefix);
                parser.fork();
                childParsers.add(parser);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return childParsers;
    }

    private void getUrlForTestSite(Element el) {
        String attr = el.attr("abs:href");
        if (!resultStore.getResult().contains(attr)) {
            resultStore.getResult().add(attr);
            url = attr;
        }
        System.out.println("size: " + resultStore.getResult().size());
    }
}