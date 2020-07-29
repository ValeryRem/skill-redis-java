package main;

import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Storage {
    private int currentId = 1;

//    private final Map<Integer, Tourist> touristsMap = new ConcurrentHashMap<>();

//    public Map<Integer, Tourist> getTouristsMap() {
//        return this.touristsMap;
//    }
    @Autowired
    private TouristRepository touristRepository;


    public synchronized Tourist addTourist (Tourist tourist) {
        int id = currentId++;
//        if(touristsMap.values().stream()
//                .filter(t -> t.getSeat().equals(tourist.getSeat()))
//                .count() > 0) {;//seats.contains(tourist.getSeat())) {
//            return null;
//        }
        AtomicInteger indicatorOfDoubleSeat = new AtomicInteger();
        touristRepository.findAll().forEach(x -> {
            if (x.getSeat().equals(tourist.getSeat())) {
                indicatorOfDoubleSeat.getAndIncrement();
            }
        });
        if(indicatorOfDoubleSeat.get() != 0) {
            return null;
        }

        if(!tourist.getBirthday().matches("\\d{4}-\\d{2}-\\d{2}")) {
            return null;
        }
        tourist.setId(id);
        touristRepository.save(tourist);
//        touristsMap.put(id, tourist);
        return tourist;
    }

    public synchronized Tourist putCorrectives(Integer id, String name, String seat, String birthday) {
        Optional<Tourist> optTourist = touristRepository.findById(id);
        if(!optTourist.isPresent()) {
            return null;
        }
        Tourist tourist = optTourist.get();
        tourist.setName(name);
        tourist.setSeat(seat);
        tourist.setBirthday(birthday);
        touristRepository.save(tourist);

//        Tourist tourist = getTourist(id);
//        if(name.length() > 0) {
//            tourist.setName(name);
//        }
//
//        if (seat.length() > 0) {
//                tourist.setSeat(seat);
//        }
//
//        if(birthday.length() > 0) {
//            tourist.setBirthday(birthday);
//        }
        return tourist;
    }

//    public synchronized Tourist deleteId(Integer id) {
//        Tourist tourist = touristsMap.get(id);
//                touristsMap.remove(id);
//                return tourist;
//    }
//
//    public synchronized void deleteAll() {
//        touristsMap.clear();
//    }
//
//    public Tourist getTourist (int touristId) {
//            return touristsMap.get(touristId);
//    }
}
