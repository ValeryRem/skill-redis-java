package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ResultStore {
    private static Set<String> result = new HashSet<>();
    private  final String initUrl;
    private Set<Parser> childParsers = new HashSet<>();
    private Set <String> taskSet = new HashSet<>();


    public ResultStore(String initUrl) {
        this.initUrl = initUrl;
    }

    public static Set<String> getResult() {
        return result;
    }

    public Set<String> getTaskSet() {
        return taskSet;
    }

    public Set<Parser> getChildParsers() {
        return childParsers;
    }

    public void output(long from, Set<String> set) throws IOException {
        Set<String> treeResult = new TreeSet<>(set);
        List<String> list = new ArrayList<>();
        list.add(initUrl);
        FileWriter writer = new FileWriter("src\\com\\company\\output.txt");
        for (String s : treeResult) {
            String span = "";
            String shift = "    ";
            int coeff = s.split("/").length - initUrl.split("/").length;
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
        System.out.println("result.size(): " + result.size());
        System.out.println("Runtime: " + (System.currentTimeMillis() - from) / 1000  + " sec.");
    }
}

