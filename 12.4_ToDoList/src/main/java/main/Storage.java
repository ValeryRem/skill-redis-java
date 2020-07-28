package main;

import main.model.Tourist;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {
    private int currentId = 1;

    private final Map<Integer, Tourist> touristsMap = new ConcurrentHashMap<>();

    public Map<Integer, Tourist> getTouristsMap() {
        return this.touristsMap;
    }

    public synchronized Tourist addTourist (Tourist tourist) {
        int id = currentId++;
        if(touristsMap.values().stream()
                .filter(t -> t.getSeat().equals(tourist.getSeat()))
                .count() > 0) {;//seats.contains(tourist.getSeat())) {
            return null;
        }
        if(!tourist.getBirthday().matches("\\d{4}-\\d{2}-\\d{2}")) {
            return null;
        }
        tourist.setId(id);
        touristsMap.put(id, tourist);
        return tourist;
    }

    public synchronized Tourist putCorrectives(Integer id, String name, String seat, String birthday) {
        Tourist tourist = getTourist(id);
        if(name.length() > 0) {
            tourist.setName(name);
        }

        if (seat.length() > 0) {
                tourist.setSeat(seat);
        }

        if(birthday.length() > 0) {
            tourist.setBirthday(birthday);
        }
        return tourist;
    }

    public synchronized Integer deleteId(Integer id) {
                touristsMap.remove(id);
                return id;
    }

    public synchronized void deleteAll() {
        touristsMap.clear();
    }

    public Tourist getTourist (int touristId) {
            return touristsMap.get(touristId);
    }
}
