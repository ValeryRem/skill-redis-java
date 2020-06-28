package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Presenter {
    private final ResultStore resultStore;

    public Presenter(ResultStore resultStore){
        this.resultStore = resultStore;
    }

    public void output(long from, Set<String> set) throws IOException {
        Set<String> treeResult = new TreeSet<>(set);
        treeResult.forEach(x -> {
            if (!x.endsWith("/")) {
                x = x.concat("/");
            }
        });
        List<String> list = new ArrayList<>();
        FileWriter writer = new FileWriter("src/com/company/output.txt");
        for (String s : treeResult) {
            String span = "";
            String shift = "    ";
            int coeff = s.split("/").length - (resultStore.getPrefix()).split("/").length;
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
