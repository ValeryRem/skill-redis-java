package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import static java.lang.Thread.sleep;


public class MyRecursiveAction extends RecursiveAction {

    private int workLoad;
    private String prefix;
    private static final int THRESHOLD = 10;
    TreeSet<String> result = new TreeSet<>();
//    ParsingHtml parsingHtml;
    String absUrl = prefix + "/";

    public MyRecursiveAction(String prefix) {
        this.prefix = prefix;
    }

    public MyRecursiveAction(int workLoad, String prefix){//, ParsingHtml parsingHtml) {
        this.workLoad = workLoad;
        this.prefix = prefix;
    }

    int index = 0;
    @Override
    protected void compute() {
        try {
            Document doc = Jsoup.connect(absUrl).maxBodySize(3_000_000).get();
            Elements elements = doc.body().getElementsByAttribute("href");
            workLoad = elements.size();
            for (Element element : elements) {
                absUrl = getURL(element);
                if (this.workLoad > THRESHOLD) {
                    List<MyRecursiveAction> subtasks = createSubtasks();
                    result.add(absUrl);
                    System.out.println(index++ + ": " +  absUrl);
                    for (int i = 0; i < subtasks.size(); i++) {
                        subtasks.get(i).fork();
                        if (i + 1 < subtasks.size()) {
                            subtasks.get(i + 1).compute();
                        }
                        try {
                            sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subtasks.get(i).join();
                    }
                } else {
                    compute();
                }
            }
        }
            catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks = new ArrayList<>();
        for (int i = 0; i < workLoad; i++) {
            subtasks.add( new MyRecursiveAction(i, absUrl));
        }
        return subtasks;
    }

    private String getURL (Element element) {
        String fullRef = absUrl;
        String suffix = element.attr("href");
                if (!suffix.startsWith("/")) {
                    suffix = "/".concat(suffix);
                }
                if (!suffix.contains("http") && suffix.length() > 3 && !suffix.startsWith("/#") && !suffix.contains("@")
                        && !suffix.contains("tel:+") && !suffix.endsWith(".pdf") && !suffix.contains("/tg:/")) {
                    fullRef = prefix + suffix; }
        return fullRef;
    }

    public void startForkJoin(String prefix) {
        MyRecursiveAction task = new MyRecursiveAction(workLoad, prefix);
        new ForkJoinPool().invoke(task);
    }

    public TreeSet<String> getResult() {
        return result;
    }
}
