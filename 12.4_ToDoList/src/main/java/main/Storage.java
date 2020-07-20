package main;

import main.model.Tourist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Tourist> addTourist (Tourist tourist) {
        int id = currentId++;
        if(!seatList.contains(tourist.getSeat())) {
            tourist.setId(id);
            seatList.add(tourist.getSeat());
            touristsMap.put(id, tourist);
        } else {
            return new ResponseEntity<>(tourist, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(tourist, HttpStatus.OK);
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
