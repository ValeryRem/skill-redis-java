package com.company;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ResultStore {
    private static Set<String> result = new HashSet<>();

    public Set<String> getResult() {
        return result;
    }
}

