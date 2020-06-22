package com.company;

import java.util.HashSet;
import java.util.Set;

public class ResultStore {
    private static Set<String> result = new HashSet<>();
//    private Set<Parser> childParsers = new HashSet<>();
//    private Set <String> taskSet = new HashSet<>();

    public Set<String> getResult() {
        return result;
    }
}

