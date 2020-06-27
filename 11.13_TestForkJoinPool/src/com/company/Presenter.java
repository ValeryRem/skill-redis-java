package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Presenter {
    private ParserContext parserContext;
    private Parser parser;
    private ResultStore resultStore;

    public Presenter(Parser parser, ResultStore resultStore, ParserContext parserContext){
        this.parser = parser;
        this.resultStore = resultStore;
        this.parserContext = parserContext;
    }

    public void output(long from, Set<String> set) throws IOException {
        Set<String> treeResult = new TreeSet<>(set);
        treeResult.forEach(x -> {
            if (!x.endsWith("/")) {
                x.concat("/");
            }
        });
        List<String> list = new ArrayList<>();
        FileWriter writer = new FileWriter("src/com/company/output.txt");
        for (String s : treeResult) {
            String span = "";
            String shift = "    ";
            int coeff = s.split("/").length - (parserContext.getPrefix()).split("/").length;
            for (int i = 0; i < coeff; i++) {
                span += shift;
            }
            list.add(span + s);
        }
        for (String str : list) {
            try {
                writer.write(str + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        System.out.println("\nResult:");
        list.forEach(System.out::println);
        System.out.println("result.size(): " + resultStore.getUrlAdded().size());
        System.out.println("Runtime: " + (System.currentTimeMillis() - from) / 1000 + " sec.");
    }
}
