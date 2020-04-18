package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;

public class Presenter {
    public Presenter() {

    }

    public void presentConnections(HashSet<List<Connection>> listHashSet) {
        Map<String, HashSet<List<Connection>>> connectionsMap = new TreeMap<>();
        connectionsMap.put("Connections", listHashSet);
        String jsonString = new Gson().toJson(connectionsMap);
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jprsr = new JsonParser();
        JsonElement jelm = jprsr.parse(jsonString);
        String connectionsJSON = gs.toJson(jelm);
        System.out.println(connectionsJSON);
    }

    public void presentStations(Map<String, Set<Station>> stationMap) {
        String jsonStr = new Gson().toJson(stationMap);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonStr);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}
