package main;

import main.model.Tourist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private static int currentId = 1;
    private static HashMap<Integer, Tourist> tourists = new HashMap<Integer, Tourist>();
    public static List<Tourist> getTourists () {
        List<Tourist> list = new ArrayList<Tourist>();
        list.addAll(tourists.values());
        return list;
    }
    public static int addTourist (Tourist tourist) {
        int id = currentId++;
        tourist.setID(id);
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
