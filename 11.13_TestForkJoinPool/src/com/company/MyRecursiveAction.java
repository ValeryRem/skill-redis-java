package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;import java.util.Arrays;
import java.util.List;import java.util.TreeSet;
import java.util.concurrent.RecursiveAction;


public class MyRecursiveAction extends RecursiveAction {

    private int workLoad;
    private String origin;
    String cssQuery;
    private static final int THRESHOLD = 10;
    TreeSet<String> result = new TreeSet<>();
    ParsingHtml parsingHtml;

    public MyRecursiveAction(int workLoad, String origin, String cssQuery, ParsingHtml parsingHtml) {
        this.workLoad = workLoad;
        this.origin = origin;
        this.cssQuery = cssQuery;
        this.parsingHtml = parsingHtml;
    }

    @Override
    protected void compute() {
        try {
            Document doc = Jsoup.connect(origin).maxBodySize(3_000_000).get();
            Elements elements = doc.body().getElementsByAttribute(cssQuery);
            workLoad = elements.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.workLoad > THRESHOLD) {
            List<MyRecursiveAction> subtasks = createSubtasks();
            for (RecursiveAction subtask : subtasks) {
                subtask.fork();
            }
        } else {
            result = parsingHtml.getHTMLinfo(origin);
        }
    }

    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks = new ArrayList<>();
        for (int i = 0; i < workLoad; i++) {
            subtasks.add( new MyRecursiveAction(i, origin, cssQuery, parsingHtml));
        }
        return subtasks;
    }
}
