package com.company;

import java.util.*;

public class ResultStore {
    private final Set<Parser> childParsers;
    private final Set<String> urlAdded;
    private final String prefix;
    private final int limitOfResult;

    public ResultStore(Set<Parser> childParsers, Set<String> urlAdded, String prefix, int limitOfResult) {
        this.childParsers = childParsers;
        this.urlAdded = urlAdded;
        this.prefix = prefix;
        this.limitOfResult = limitOfResult;
    }

    public Set<Parser> getChildParsers() {
        return childParsers;
    }

    public Set<String> getUrlAdded() {
        return urlAdded;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLimitOfResult() {
        return limitOfResult;
    }
}

