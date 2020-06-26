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

public class Parser extends RecursiveAction {

    private String url;
    private ResultStore resultStore = new ResultStore();
    private ParserContext parserContext;
    private Set<String> urlAdded;
    private boolean shouldStop = false;

    public Parser(String url, Set<String> urlAdded, ParserContext parserContext) {
        this.url = url.trim();
        this.urlAdded = urlAdded;
        this.parserContext = parserContext;
    }

    @Override
    protected void compute() {
            Set<Parser> subTaskSet = parseAndGetTasksForChildren();
            for (Parser task : subTaskSet) {
                    System.out.println(Thread.currentThread().getName() + " -> task url: " + task.url + "- result: " + urlAdded.size());
                    task.invoke();
            }
            try {
                output(parserContext.getFrom(), urlAdded);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private Set<Parser> parseAndGetTasksForChildren() {
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(url).maxBodySize(3_000_000).userAgent("Mozilla").get();
            elements = doc.select("a");
            for (Element element : elements) {
                    processElement(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStore.getChildParsers();
    }

    private boolean shouldStop() {
        if (urlAdded.size() >= parserContext.getLIMIT_OF_RESULT()) {
            shouldStop = true;
        }
        return shouldStop;
    }

    private void processElement(Element el) {
        if(shouldStop()){
            try {
                output(parserContext.getFrom(), urlAdded);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Set of results is overloaded!");
//            return;
            System.exit(0);
        } else {
            String attr = el.attr("abs:href");
            if (attr.contains(parserContext.getPrefix()) && !attr.contains(".pdf") && !urlAdded.contains(attr) && !attr.contains("#")
                    && !attr.contains("@") && !attr.contains("tel:") && attr.endsWith("/")) {
                Parser parser = new Parser(attr, urlAdded, parserContext);
                urlAdded.add(attr);
                System.out.println(Thread.currentThread().getName() + " -> " + attr + " - Result size: " + urlAdded.size());
                if (!resultStore.getTaskSet().contains(parser.url)) {
                    resultStore.getTaskSet().add(parser.url);
                    resultStore.getChildParsers().add(parser);
                    parser.fork();
                }
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
            int coeff = s.split("/").length - (parserContext.getPrefix() + "/").split("/").length;
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