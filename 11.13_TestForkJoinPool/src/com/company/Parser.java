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
    long from;
    private ResultStore resultStore = new ResultStore(prefix + "/");
//    private Set<String> taskSet = new HashSet<>();
//    private Set<Parser> childSet = new HashSet<>();
    private final int LIMIT = 1000;

    public Parser(long from, String url, String prefix) {
        this.from = from;
        this.url = url.trim();
        this.prefix = prefix;
    }

    @Override
    protected Set<String> compute() {
        parseAndGetTasksForChilds(); // создаем для данного url сет задач (дочерних ссылок для парсинга)
        Set<Parser> subTaskSet = resultStore.getChildParsers(); // передаем сет задач в метод
        if (ResultStore.getResult().size() < LIMIT) {
            subTaskSet.stream().
                    filter(task -> !resultStore.getTaskSet().contains(task.url)). // проверяем task на уникальность
                    forEach(task -> {
                resultStore.getTaskSet().add(task.url); // добавляем новый запущенный task в реестр учета задач
                System.out.println(Thread.currentThread().getName() + " -> task url: " + task.url + "- result: " + resultStore.getResult().size());
                ResultStore.getResult().addAll(task.join());

            });
        } else {
            System.out.println("Set of results is overloaded!");
            try {
                resultStore.output(from, ResultStore.getResult());
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return ResultStore.getResult();
    }

    private Set<Parser> parseAndGetTasksForChilds() {
        // здесь загружаем и парсим this.url и создаем массив задач для каждой дочерней ссылки.
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(url).maxBodySize(3_000_000).userAgent("Mozilla").get();
            elements = doc.select("a");  //getElementsByAttribute("href");//
            for (Element element : elements) {
                if (ResultStore.getResult().size() < LIMIT) {
                    processElement(element);
                } else {
                    System.out.println("Set of references is full!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStore.getChildParsers();
    }

    private void processElement(Element el) {
//        String fullRef;
//        String suffix = el.attr("href");
//        if (!suffix.startsWith("/")) {
//            suffix = "/".concat(suffix);
//        }
//        if (!suffix.contains("http") && suffix.length() > 3 && !suffix.contains("#") && !suffix.contains("@")
//                && !suffix.contains("tel:+") && !suffix.endsWith(".pdf") && !suffix.contains("/tg:/")) {
//            fullRef = prefix + suffix;
//            if (!resultStore.getResult().contains(fullRef)) {
//                Parser parser = new Parser(from, fullRef, prefix);
//                resultStore.getResult().add(fullRef);
//                parser.fork();
//                childSet.add(parser);
//                System.out.println(Thread.currentThread().getName() + " -> " + fullRef + " size of result: " +
//                        resultStore.getResult().size());
//            }
//        }

        // For testing site:
        String attr = el.attr("abs:href");
        if (attr.contains(prefix) && !attr.contains(".pdf") && !ResultStore.getResult().contains(attr) && !attr.contains("#")
        && !attr.contains("@") && !attr.contains("tel:")) { // устраняем чуждые ссылки
            Parser parser = new Parser(from, attr, prefix); // создаем Parser для каждой уникальной ссылки
            ResultStore.getResult().add(attr);//resultStore.getResult().add(attr);
            System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + ResultStore.getResult().size());
            resultStore.getChildParsers().add(parser);// дабавляем объект Parser в коллекцию
            parser.fork(); // запускаем асинхронное исполнение задачи в общем пуле потоков ForkJoinPool
        }
    }
}