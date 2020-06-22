package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class Parser extends RecursiveTask<Set<String>> {

    private String url;
    private String prefix;
    private ResultStore resultStore = new ResultStore();
    private Set<String> result = new HashSet<>();

    public Parser(String url, String prefix) {
        this.url = url.trim();
        this.prefix = prefix;
    }

    @Override
    protected Set<String> compute() {
        parseAndGetTasksForChilds(); // создаем для данного url сет задач (дочерних ссылок для парсинга)
        Set<Parser> subTaskSet = resultStore.getChildParsers(); // передаем сет задач в метод
        for (Parser task : subTaskSet) {  // для каждой задачи...
            if (!resultStore.getTaskSet().contains(task)){ // проверяем, выполнялся ли раньше данный task
                resultStore.getTaskSet().add(task); // добавляем новый выполненный task в реестр учета выполненных задач
                System.out.println(Thread.currentThread().getName() + " -> taskSet: " + resultStore.getTaskSet().size());
                resultStore.getResult().addAll(task.join());
            }
        }
        return result;
    }

    private Set<Parser> parseAndGetTasksForChilds() {
        // здесь загружаем и парсим this.url и создаем массив задач для каждой дочерней ссылки.
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(url).maxBodySize(3_000_000).userAgent("Mozilla").get();
            elements = doc.select("a");  //getElementsByAttribute("href");//
            for (Element element : elements) {
                processElement(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStore.getChildParsers();
    }

    private void processElement(Element el) {
        String attr = el.attr("abs:href");
        if (!attr.equals(prefix + "/")) {
            Parser parser = new Parser(attr, prefix); // создаем Parser для каждой ссылки и дабавляем их в коллекцию Parser:
            resultStore.getResult().add(attr);
            parser.fork();
            resultStore.getChildParsers().add(parser);
        }
        System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + resultStore.getResult().size());
    }
}