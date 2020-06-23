package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ResultStore {
//    private static Set<String> result = new HashSet<>();
    private  final String initUrl;
    private Set<Parser> childParsers = new HashSet<>();
    private Set <String> taskSet = new HashSet<>();


    public ResultStore(String initUrl) {
        this.initUrl = initUrl;
    }

    public Set<String> getTaskSet() {
        return taskSet;
    }

    public Set<Parser> getChildParsers() {
        return childParsers;
    }
}

