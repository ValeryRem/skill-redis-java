package com.company;

public class Station {
    String nameOfStation, line;

    public Station (String nameOfStation, String line) {
        this.nameOfStation = nameOfStation;
        this.line = line;
    }

    public String getNameOfStation() {
        return nameOfStation;
    }

    public String getLine() {
        return line;
    }
}
