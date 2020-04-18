package com.company;

import java.util.List;

public class Station {
    String name, line;
    List<String> stationsConnected;

    public Station(String nameOfStation, String lineOwn, List<String> stationsConnected) {
        this.name = nameOfStation;
        this.line = lineOwn;
        this.stationsConnected = stationsConnected;
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

    public List<String> getStationsConnected() {
        return stationsConnected;
    }
}
