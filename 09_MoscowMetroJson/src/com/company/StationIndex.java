package com.company;

import java.util.*;

public class StationIndex {
    Map<String, List<String>> stationsMap;
//    Connection[] hubsArray;
List <List<String>> hubs;
    List <Line> lineList;

    public StationIndex (Map<String, List<String>> stationsMap,  List <Line> lineList,  List <List<String>> hubs){
        this.stationsMap = stationsMap;
        this.lineList = lineList;
        this.hubs = hubs;
    }
}
