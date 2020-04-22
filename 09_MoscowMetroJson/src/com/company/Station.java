package com.company;

import java.util.List;

public class Station {
    String name, line;


    public Station(String lineOwn, String nameOfStation) {
        this.name = nameOfStation;
        this.line = lineOwn;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public void setName(String name) {
        this.name = name;
    }
}
