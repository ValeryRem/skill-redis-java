package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.RecursiveTask;

public class Parser extends RecursiveTask<Set<String>> {

    private String url;
    private String prefix;
    long from;
    private ResultStore resultStore = new ResultStore(prefix + "/");
    private Set<String> urlAdded;
    private final int LIMIT_OF_RESULT;

    public Parser(long from, String url, String prefix, Set<String> urlAdded, int LIMIT_OF_RESULT) {
        this.from = from;
        this.url = url.trim();
        this.prefix = prefix;
        this.urlAdded = urlAdded;
        this.LIMIT_OF_RESULT = LIMIT_OF_RESULT;
    }

    @Override
    protected Set<String> compute() {
       // создаем для данного url сет задач (дочерних ссылок для парсинга)
        Set<Parser> subTaskSet =  parseAndGetTasksForChilds(); //resultStore.getChildParsers(); // передаем сет задач в метод
        if (urlAdded.size() < LIMIT_OF_RESULT) {
            subTaskSet.stream().
                    filter(task -> !resultStore.getTaskSet().contains(task.url)). // проверяем task на уникальность
                    forEach(task -> {
                resultStore.getTaskSet().add(task.url); // добавляем новый запущенный task в реестр учета задач
                System.out.println(Thread.currentThread().getName() + " -> task url: " + task.url + "- result: " + urlAdded.size());
                urlAdded.addAll(task.join());
                    });
        } else {
            System.out.println("Set of results is overloaded!");
            try {
                output(from, urlAdded);
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urlAdded;
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
        if (attr.contains(prefix) && !attr.contains(".pdf") && !urlAdded.contains(attr) && !attr.contains("#")
        && !attr.contains("@") && !attr.contains("tel:")) { // устраняем чуждые ссылки
            urlAdded.add(attr);
            Parser parser = new Parser(from, attr, prefix, urlAdded, LIMIT_OF_RESULT); // создаем Parser для каждой уникальной ссылки
            System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + urlAdded.size());
            resultStore.getChildParsers().add(parser);// дабавляем объект Parser в коллекцию
            parser.fork(); // запускаем асинхронное исполнение задачи в общем пуле потоков ForkJoinPool
        }
    }

    public void output(long from, Set<String> set) throws IOException {
        Set<String> treeResult = new TreeSet<>(set);
        List<String> list = new ArrayList<>();
        FileWriter writer = new FileWriter("src\\com\\company\\output.txt");
        for (String s : treeResult) {
            String span = "";
            String shift = "    ";
            int coeff = s.split("/").length - (prefix + "/").split("/").length;
            for (int i = 0; i < coeff; i++) {
                span += shift ;
            }
            list.add(span + s);
        }
        for(String str: list) {
            try {
                writer.write(str + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        System.out.println("\nResult:");
        list.forEach(System.out::println);
        System.out.println("result.size(): " + urlAdded.size());
        System.out.println("Runtime: " + (System.currentTimeMillis() - from) / 1000  + " sec.");
    }
}