package com.company;

public class ParserContext {
    private long from;
    private String prefix;
    private int LIMIT_OF_RESULT;

    public ParserContext(long from, String prefix, int LIMIT_OF_RESULT) {
        this.from = from;
        this.prefix = prefix;
        this.LIMIT_OF_RESULT = LIMIT_OF_RESULT;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLIMIT_OF_RESULT() {
        return LIMIT_OF_RESULT;
    }
}
