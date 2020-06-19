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
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;


public class MyRecursiveAction extends RecursiveTask<TreeSet<String>> {
    private String prefix;
    private String url;
    private Elements elements;
    private final int LIMIT_OF_RESULT = 500;
    private final int THRESHOLD = 100;
    private final int DEPTH = 30;
    private int start;
    private int end;
    private final TreeSet<String> result = new TreeSet<>();

    public MyRecursiveAction(String prefix, Elements elements, int start, int end) {
        this.prefix = prefix;
        this.elements = elements;
        this.start = start;
        this.end = end;
        url = prefix + "/";
        result.add(url);
    }


    @Override
    protected TreeSet<String> compute() {
        if (result.size() <= LIMIT_OF_RESULT) {
            TreeSet<String> set = new TreeSet<>();
            try {
                Elements elements = getElements(url);
                int workLoad = elements.size();
                if (workLoad < THRESHOLD) {
                    TreeSet<String> interimSet = getUrlSet(elements);
                    set.addAll(interimSet);
                    interimSet.forEach(x -> {
                        try {
                            Elements els = getElements(x);
                            MyRecursiveAction myRecursiveAction = new MyRecursiveAction(prefix, els, 0, els.size());
//                            myRecursiveAction.compute();
                            set.addAll(getUrlSet(els));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Elements elements1 = elements.stream().
                            limit(workLoad / 2).
                            collect(Collectors.toCollection(Elements::new));
                    MyRecursiveAction firstTask = new MyRecursiveAction(prefix, elements1, 0, workLoad / 2);
                    set.addAll(getUrlSet(elements1));
                    firstTask.fork(); //start asynchronously

                    Elements elements2 = elements.stream().
                            skip(workLoad / 2).
                            collect(Collectors.toCollection(Elements::new));
                    MyRecursiveAction secondTask = new MyRecursiveAction(prefix, elements2, workLoad / 2 + 1, workLoad);
//                set.addAll(getUrlSet(elements2));
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
        } else {
            System.out.println("Result set is full!");
        }
        return result;
    }

    private Elements getElements(String url) throws IOException {
        Document doc = Jsoup.connect(url).maxBodySize(3_000_000).get();
        return doc.body().getElementsByAttribute("href");
    }

    //    private List<MyRecursiveAction> createSubtasks() {
//        List<MyRecursiveAction> subtasks = new ArrayList<>();
//        for (int i = 0; i < workLoad; i++) {
//            subtasks.add( new MyRecursiveAction(i, absUrl));
//        }
//        return subtasks;
//    }

    private int index = 0;

    private TreeSet<String> getUrlSet(Elements elements) {
        TreeSet<String> set = new TreeSet<>();
        String fullRef;
        for (Element element : elements) {
            String suffix = element.attr("href");
            if (!suffix.startsWith("/")) {
                suffix = "/".concat(suffix);
            }
            if (!suffix.contains("http") && suffix.length() > 3 && !suffix.contains("#") && !suffix.contains("@")
                    && !suffix.contains("tel:+") && !suffix.endsWith(".pdf") && !suffix.contains("/tg:/")
                    && !result.contains(suffix)) {
                fullRef = prefix + suffix;
                set.add(fullRef);
                url = fullRef;
                result.add(fullRef);
                index++;
//                System.out.println(suffix + " -> " + index);
                if (index == DEPTH + 1) {
                    index = 0;
                    continue;
                }
                System.out.println(Thread.currentThread().getName() + " -> " + fullRef + " size of result: " + result.size());
            }
        }
        return set;
    }

    public TreeSet<String>  startForkJoin() {
        ForkJoinTask<TreeSet<String>> task = new MyRecursiveAction(prefix, elements, start, end);
        return new ForkJoinPool().invoke(task);
    }
}
