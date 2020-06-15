package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.RecursiveAction;

public class MyRecursiveAction extends RecursiveAction {

    private int workLoad;
    private String origin;
    String cssQuery;
    private static final int THRESHOLD = 10;
    ParsingHtml parsingHtml = new ParsingHtml(origin, cssQuery);
//    TreeSet<String> result;

    public MyRecursiveAction(int workLoad, String origin, String cssQuery) {
        this.workLoad = workLoad;
        this.origin = origin;
        this.cssQuery = cssQuery;
    }

    @Override
    protected void compute() {
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();
            Elements elements = doc.body().getAllElements();
            workLoad = elements.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.workLoad > THRESHOLD) {
            List<MyRecursiveAction> subtasks = new ArrayList<>(createSubtasks());
            for (RecursiveAction subtask : subtasks) {
                subtask.fork();
            }
        } else {
            parsingHtml.getHTMLinfo(origin);
        }
    }

    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks = new ArrayList<>();
        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2, origin, cssQuery);
        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2, origin, cssQuery);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        return subtasks;
    }
}