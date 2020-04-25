package com.company;

public class Line {
    String lineNumber;
    String lineName;

    public Line (){
    }

    public Line (String lineNumber, String lineName) {
        this.lineNumber = lineNumber;
        this.lineName = lineName;
    }

    public boolean equals(Line o) {
        if (this == o) return true;
        return o != null &&
                o.lineNumber.equals(lineNumber)
                && o.lineName.equals(lineName);
    }
}
