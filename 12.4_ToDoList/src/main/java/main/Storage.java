package main;

import main.model.Tourist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    private static int currentId = 1;
    public static Map<Integer, Tourist> touristsMap = new ConcurrentHashMap<>();

    public static List<Tourist> getTouristsMap() {
        List<Tourist> list = new ArrayList<>(touristsMap.values());
        return list;
    }

    public static int addTourist (Tourist tourist) {
        int id = currentId++;
        tourist.setId(id);
        touristsMap.put(id, tourist);
        return id;
    }

    public static Tourist getTourist (int touristId) {
        if(touristsMap.containsKey(touristId)) {
            return touristsMap.get(touristId);
        }
        return null;
    }


}
