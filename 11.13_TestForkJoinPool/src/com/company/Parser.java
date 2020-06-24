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
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Parser extends RecursiveAction {

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
    protected void compute() {
        Set<Parser> subTaskSet =  parseAndGetTasksForChilds();
            for (Parser task : subTaskSet) {
                if (!resultStore.getTaskSet().contains(task.url)) {
                    resultStore.getTaskSet().add(task.url);
                    System.out.println(Thread.currentThread().getName() + " -> task url: " + task.url + "- result: " + urlAdded.size());
                    task.invoke();
                }
            }
    }

    private Set<Parser> parseAndGetTasksForChilds() {
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
        if (urlAdded.size() >= LIMIT_OF_RESULT) {
            System.out.println("Set of results is overloaded!");
            try {
                output(from, urlAdded);
//                System.exit(0);
                notifyAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String attr = el.attr("abs:href");
            if (attr.contains(prefix) && !attr.contains(".pdf") && !urlAdded.contains(attr) && !attr.contains("#")
                    && !attr.contains("@") && !attr.contains("tel:")) {
                urlAdded.add(attr);
                Parser parser = new Parser(from, attr, prefix, urlAdded, LIMIT_OF_RESULT);
                System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + urlAdded.size());
                resultStore.getChildParsers().add(parser);// дабавляем объект Parser в коллекцию
                parser.fork();
            }
        }
    }

    public void output(long from, Set<String> set) throws IOException {
        Set<String> treeResult = new TreeSet<>(set);
        List<String> list = new ArrayList<>();
        FileWriter writer = new FileWriter("src/com/company/output.txt");
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