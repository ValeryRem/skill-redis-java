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

    public ResponseEntity<Tourist> addTourist (Tourist tourist) {
        System.out.printf("%s%s%s%s\n", "Add tourist: name: ", tourist.getName(), "  seat: ", tourist.getSeat());
        int id = currentId++;
        if(!seatList.contains(tourist.getSeat())) {
            tourist.setId(id);
            seatList.add(tourist.getSeat());
            touristsMap.put(id, tourist);
        } else {
            return new ResponseEntity<>(tourist, HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("Added tourist: id = " + id);
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    public synchronized ResponseEntity<Tourist> putNewData(Integer id, String name, String seat, String birthday) {
        Tourist tourist = getTourist(id);
        if(name.length() > 0) {
            Objects.requireNonNull(tourist).setName(name);
        }
        if(!seat.equals("0")) {
            if (!seatList.contains(seat) || tourist.getSeat().equals(seat)) {
                tourist.setSeat(seat);
            } else {
                System.out.println("This seat is occupied. Change seat number!");
            }
        }
        if(birthday.length() > 0) {
            Objects.requireNonNull(tourist).setBirthday(birthday);
        }
//        touristRepository.save(Objects.requireNonNull(tourist));
        return new ResponseEntity<>(tourist, HttpStatus.ACCEPTED);
    }

    public synchronized ResponseEntity<Integer> delete(Integer id) {
        if (id > 0) {
            if (touristsMap.containsKey(id)) {
                String seat = touristsMap.get(id).getSeat();
                System.out.println("Tourist removed: " + touristsMap.remove(id).getName());
                System.out.println("SeatList before: "  + Arrays.toString(seatList.toArray()));
                System.out.println("Connected seat to remove: " + seat);
                seatList.remove(seat);
                System.out.println("SeatList after: "  + Arrays.toString(seatList.toArray()));
            } else {
                return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
            }
        } else {
            touristsMap.clear();
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    public ResponseEntity<Tourist> get(Integer id) {
        Tourist tourist = getTourist(id);
//        return ResponseEntity.of(Optional.ofNullable(tourist));
//    }
        if (tourist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(tourist, HttpStatus.OK);
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
