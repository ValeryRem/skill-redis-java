package com.company;

public class ParserContext {
    private long from;
    private String prefix;
    private int limitOfResult;

    public ParserContext(long from, String prefix, int limitOfResult) {
        this.from = from;
        this.prefix = prefix;
        this.limitOfResult = limitOfResult;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLimitOfResult() {
        return limitOfResult;
    }
}
