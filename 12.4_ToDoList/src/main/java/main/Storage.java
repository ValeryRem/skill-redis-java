package main;

import main.model.Tourist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {
    private int currentId = 1;

    private final Map<Integer, Tourist> touristsMap = new ConcurrentHashMap<>();
    private final List<String> seatList = new ArrayList<>();
//    Collection<Tourist> tourists = Storage.touristsMap.values();

    public Map<Integer, Tourist> getTouristsMap() {
        return this.touristsMap;
    }

    public synchronized ResponseEntity<Tourist> addTourist (Tourist tourist) {
        System.out.printf("%s%s%s%s\n", "Add tourist: name: ", tourist.getName(), "  seat: ", tourist.getSeat());
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

    public synchronized Tourist putCorrectives(Integer id, String name, String seat, String birthday) {
        Tourist tourist = getTourist(id);
        if(name.length() > 0) {
            tourist.setName(name);
        }

        if (seat.length() > 0 && !tourist.getSeat().equals(seat)) {
                tourist.setSeat(seat);
        }

        if(birthday.length() > 0) {
            tourist.setBirthday(birthday);
        }
//        touristRepository.save(Objects.requireNonNull(tourist));
        return tourist;//new ResponseEntity<>(tourist, HttpStatus.ACCEPTED);
    }

    public synchronized Integer delete(Integer id) {
        if (id > 0) {
            if (touristsMap.containsKey(id)) {
                String seat = touristsMap.get(id).getSeat();
                seatList.remove(seat);
            } else {
                return id;
            }
        } else {
            touristsMap.clear();
        }
        return id;
    }

    public List<String> getSeatList() {
        return seatList;
    }

    public Tourist getTourist (int touristId) {
        if(touristsMap.containsKey(touristId)) {
            return touristsMap.get(touristId);
        }
        return null;
    }
}
