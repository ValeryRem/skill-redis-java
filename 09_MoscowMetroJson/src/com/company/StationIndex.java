package com.company;

import java.util.*;

public class StationIndex {
    Map<String, List<String>> stationsMap;
    List<Connection> hubsList;
    List <Line> lineList;

    public StationIndex (Map<String, List<String>> stationsMap,  List <Line> lineList, List<Connection> hubsList){
        this.stationsMap = stationsMap;
        this.lineList = lineList;
        this.hubsList = hubsList;
    }
}
