package com.company;

import java.util.*;

public class ResultStore {
    private String initUrl;
    private Set<Parser> childParsers = new HashSet<>();
    private Set <String> taskSet = new HashSet<>();

    public ResultStore() {
    }

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

