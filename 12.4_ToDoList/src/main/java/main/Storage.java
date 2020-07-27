package main;

import main.model.Tourist;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {
    private int currentId = 1;

    private final Map<Integer, Tourist> touristsMap = new ConcurrentHashMap<>();
    private final Set<String> seats = new HashSet<>();
//    Collection<Tourist> tourists = Storage.touristsMap.values();

    public Map<Integer, Tourist> getTouristsMap() {
        return this.touristsMap;
    }

    public synchronized Tourist addTourist (Tourist tourist) {
//        System.out.printf("%s%s%s%s\n", "Add tourist: name: ", tourist.getName(), "  seat: ", tourist.getSeat());
        int id = currentId++;
        if(seats.contains(tourist.getSeat())) {
            return null;
        }
        if(!tourist.getBirthday().matches("\\d{4}-\\d{2}-\\d{2}")) {
            return null;
        }
        tourist.setId(id);
        seats.add(tourist.getSeat());
        touristsMap.put(id, tourist);
        return tourist;
    }

    public synchronized Tourist putCorrectives(Integer id, String name, String seat, String birthday) {
        Tourist tourist = getTourist(id);
        if(name != null) {
            tourist.setName(name);
        }

        if (seat != null && !tourist.getSeat().equals(seat)) {
                seats.remove(tourist.getSeat());
                tourist.setSeat(seat);
        }

        if(birthday != null) {
            tourist.setBirthday(birthday);
        }
//        System.out.printf("%s%s%s%s%s%s\n", "Corrected tourist's data: name: ", tourist.getName(), "  seat: ", tourist.getSeat(), " birthday: ",
//                tourist.getBirthday());
//        touristRepository.save(Objects.requireNonNull(tourist));
        return tourist;//new ResponseEntity<>(tourist, HttpStatus.ACCEPTED);
    }

    public synchronized Integer delete(Integer id) {
        if (id > 0) {
            if (touristsMap.containsKey(id)) {
                String seat = touristsMap.get(id).getSeat();
                seats.remove(seat);
//                seatList.removeIf(touristsMap.get(id).getSeat()::equals);
                touristsMap.remove(id);
            }
//            } else {
//                return id;
//            }
        } else {
            touristsMap.clear();
        }
        return id;
    }

    public Set<String> getSeats() {
        return seats;
    }

    public Tourist getTourist (int touristId) {
        if(touristsMap.containsKey(touristId)) {
            return touristsMap.get(touristId);
        }
        return null;
    }
}
