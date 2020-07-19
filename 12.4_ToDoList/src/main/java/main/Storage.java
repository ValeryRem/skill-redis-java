package main;

import main.model.Tourist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {
    private int currentId = 1;

    private Map<Integer, Tourist> touristsMap = new ConcurrentHashMap<>();
    private List<Integer> seatList = new ArrayList<>();
//    Collection<Tourist> tourists = Storage.touristsMap.values();

    public Map<Integer, Tourist> getTouristsMap() {
        return this.touristsMap;
    }

    public synchronized void addTourist (Tourist tourist) {
        int id;
        if(!seatList.contains(tourist.getSeat())) {
            id = currentId++;
            tourist.setId(id);
            seatList.add(tourist.getSeat());
            touristsMap.put(id, tourist);
        } else {
            System.out.println("The seat of this new Tourist is occupied. Change his seat number!");
        }
    }

    public List<Integer> getSeatList() {
        return seatList;
    }

    public Tourist getTourist (int touristId) {
        if(touristsMap.containsKey(touristId)) {
            return touristsMap.get(touristId);
        }
        return null;
    }


}
