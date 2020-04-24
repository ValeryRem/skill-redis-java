package com.company;

public class Station {
    String name, lineNumber;


    public Station(String lineNumber, String nameOfStation) {
        this.name = nameOfStation;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return lineNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
}
