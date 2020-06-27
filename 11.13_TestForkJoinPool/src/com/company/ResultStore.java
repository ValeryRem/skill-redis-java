package com.company;

import java.util.*;

public class ResultStore {
    private Set<Parser> childParsers;
    private Set <String> taskSet;
    private Set<String> urlAdded;

    public ResultStore(Set<Parser> childParsers, Set<String> urlAdded, Set<String> taskSet) {
        this.childParsers = childParsers;
        this.urlAdded = urlAdded;
        this.taskSet = taskSet;
    }

    public Set<String> getTaskSet() {
        return taskSet;
    }

    public Set<Parser> getChildParsers() {
        return childParsers;
    }

    public Set<String> getUrlAdded() {
        return urlAdded;
    }
}

