package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class Parser extends RecursiveTask<Set<String>> {

    private String url;
    private String prefix;
    private ResultStore resultStore = new ResultStore();
    private Set<Parser> taskSet = new HashSet<>();
    private Set<Parser> childSet = new HashSet<>();

    public Parser(String url, String prefix) {
        this.url = url.trim();
        this.prefix = prefix;
    }

    @Override
    protected Set<String> compute() {
        parseAndGetTasksForChilds(); // создаем для данного url сет задач (дочерних ссылок для парсинга)
        Set<Parser> subTaskSet = childSet; // передаем сет задач в метод
        subTaskSet.stream().
                filter(task -> !taskSet.contains(task)). // проверяем task на уникальность
                forEach(task -> {
                    taskSet.add(task); // добавляем новый выполненный task в реестр учета выполненных задач
                    System.out.println(Thread.currentThread().getName() + " -> task url: " + task.url + " - " + task.toString());//resultStore.getTaskSet().size());
            resultStore.getResult().addAll(task.join());
        });
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
                processElement(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return childSet;
    }

    private void processElement(Element el) {
        String attr = el.attr("abs:href");
        if (!attr.equals(prefix + "/") && !resultStore.getResult().contains(attr)) { // устраняем дублирующие ссылки
            Parser parser = new Parser(attr, prefix); // создаем Parser для каждой уникальной ссылки
            resultStore.getResult().add(attr);//resultStore.getResult().add(attr);
            parser.fork(); // запускаем асинхронное исполнение задачи в общем пуле потоков ForkJoinPool
            childSet.add(parser);// дабавляем объект Parser в коллекцию
        }
        System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + resultStore.getResult().size());
    }
}