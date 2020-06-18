package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static java.lang.Thread.sleep;


public class MyRecursiveAction extends RecursiveTask<TreeSet<String>> {

    private final String prefix;
    private String url;
    private final int THRESHOLD = 10;
//    private final int depthOfParsing = 30;
//    private final int limitOfResultList = 500;
    private final int start;
    private final int end;
    private final TreeSet<String> result = new TreeSet<>();

    public MyRecursiveAction(String prefix, int start, int end) {
        this.prefix = prefix;
        this.start = start;
        this.end = end;
        url = prefix + "/";
        result.add(url);
    }


    @Override
    protected TreeSet<String> compute() {
        TreeSet<String> set = new TreeSet<>();
        try {
            Document doc = Jsoup.connect(url).maxBodySize(3_000_000).get();
            Elements elements = doc.body().getElementsByAttribute("href");
            int workLoad = elements.size();
            if (workLoad < THRESHOLD) {
                    set.addAll(getUrl(elements));
            } else {
                    MyRecursiveAction firstTask = new MyRecursiveAction(prefix, 0, workLoad / 2);
                    firstTask.fork(); //start asynchronously
                    MyRecursiveAction secondTask = new MyRecursiveAction(prefix, workLoad / 2 + 1, workLoad);
                    set.addAll(secondTask.compute());
                    firstTask.join();
            }
//                System.out.println(index++ + ": " + absUrl);
//                    for (int i = 0; i < subtasks.size(); i++) {
//                        subtasks.get(i).fork();
//                        if (i + 1 < subtasks.size()) {
//                            subtasks.get(i + 1).compute();
//                        }
//                        try {
//                            sleep(150);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        subtasks.get(i).join();
//                    }
//                }}
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.addAll(set);
        return result;
    }

//    private List<MyRecursiveAction> createSubtasks() {
//        List<MyRecursiveAction> subtasks = new ArrayList<>();
//        for (int i = 0; i < workLoad; i++) {
//            subtasks.add( new MyRecursiveAction(i, absUrl));
//        }
//        return subtasks;
//    }
    private final int index = 0;

    private TreeSet<String> getUrl(Elements elements) {
        TreeSet<String> set = new TreeSet<>();
        String fullRef = prefix;
        for (Element element : elements) {
            String suffix = element.attr("href");
            if (!suffix.startsWith("/")) {
                suffix = "/".concat(suffix);
            }

            if (!suffix.contains("http") && suffix.length() > 3 && !suffix.startsWith("/#") && !suffix.contains("@")
                    && !suffix.contains("tel:+") && !suffix.endsWith(".pdf") && !suffix.contains("/tg:/")
                    && !result.contains(suffix)) {
                fullRef = prefix + suffix;
                set.add(fullRef);
                url = fullRef;
                System.out.println(Thread.currentThread().getName() + " -> " + fullRef);
//                index++;
//                System.out.println(suffix + " -> " + index);
//                while (result.size() < limitOfResultList) {
//                    if (index == depthOfParsing) {
//                        index = 0;
//                        continue;
//                    }
//                    result.add(fullRef);
//                }
            }
        }
        return set;
    }

    public TreeSet<String>  startForkJoin() {
        ForkJoinTask<TreeSet<String>> task = new MyRecursiveAction(prefix, start, end);
        return new ForkJoinPool().invoke(task);
    }
}
