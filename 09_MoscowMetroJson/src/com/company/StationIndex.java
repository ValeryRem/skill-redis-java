package com.company;

import java.util.*;

public class StationIndex {
    Map<String, List<String>> stationsMap;
    List<Connection> jointStationsList;

    public StationIndex (Map<String, List<String>> stationsMap, List<Connection> jointStationsList){
        this.stationsMap = stationsMap;
        this.jointStationsList = jointStationsList;
    }
}
