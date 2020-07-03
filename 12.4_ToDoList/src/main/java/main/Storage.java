package main;

import main.model.Tourist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    private static int currentId = 1;
    private static Map<Integer, Tourist> tourists = new ConcurrentHashMap<>();

    public static List<Tourist> getTourists () {
        List<Tourist> list = new ArrayList<>(tourists.values());
        return list;
    }
    public static int addTourist (Tourist tourist) {
        int id = currentId++;
        tourist.setId(id);
        tourists.put(id, tourist);
        return id;
    }

    public static Tourist getTourist (int touristId) {
        if(tourists.containsKey(touristId)) {
            return tourists.get(touristId);
        }
        return null;
    }
}
