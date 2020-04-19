package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Connection {
    List<Connection> listConnections;//
    String line, station;

    public Connection() {
    }

    public Connection(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public HashSet<List<Connection>> connectionSetGenerate(List<Station> stationList) {
        HashSet<List<Connection>> result = new HashSet<>();
        for (int i = 0; i < stationList.size(); i++) {
            listConnections = new ArrayList<>();
            Connection connection = new Connection(stationList.get(i).line, stationList.get(i).name);
            listConnections.add(connection);
            for (int k = i + 1; k < stationList.size(); k++) {
                List<String> stationsConnected = stationList.get(k).stationsConnected;
                if (stationsConnected != null) {
                    for (String s : stationsConnected) {
                        Station stationPrime = stationList.get(i);
                        if (s.contains(stationPrime.name)) {
                            Connection newConnection = new Connection(stationList.get(k).line, stationList.get(k).name);
                            listConnections.add(newConnection);
                        }
                    }
                }
            }
            if (listConnections.size() > 1) {
                result.add(listConnections);
            }
        }
        return result;
    }

    public List<Connection> getListConnections() {
        return listConnections;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }
}

