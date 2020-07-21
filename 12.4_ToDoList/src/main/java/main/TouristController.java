package main;

import main.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@RestController
public class TouristController {
    @Autowired
    Storage storage;// = new Storage();
//    @Autowired
//    private TouristRepository touristRepository;

    @PostMapping("/tourists/")
    public synchronized ResponseEntity<Tourist> addTourist (Tourist tourist) {
    //        Tourist  newTourist = touristRepository.save(tourist);
       return storage.addTourist(tourist);
    }

    @GetMapping("/tourists/")
    public Collection<Tourist> list() {
        return  storage.getTouristsMap().values();
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> get(@PathVariable("id") Integer id) {
        Tourist tourist = storage.getTourist(id);
//        return ResponseEntity.of(Optional.ofNullable(tourist));
//    }
            if (tourist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    @PutMapping("/tourists/{id}")
    public synchronized ResponseEntity<Tourist> putNewData(@PathVariable("id")Integer id, String name, String seat, String birthday) {
        Tourist tourist = storage.getTourist(id);
        if(name.length() > 0) {
            Objects.requireNonNull(tourist).setName(name);

        }
        if(!seat.equals("0")) {
            if (!storage.getSeatList().contains(seat) || tourist.getSeat().equals(seat)) {
                tourist.setSeat(seat);
            } else {
                System.out.println("This seat is occupied. Change seat number!");
            }
        }
        if(birthday.length() >0) {
            Objects.requireNonNull(tourist).setBirthday(birthday);
        }
//        touristRepository.save(Objects.requireNonNull(tourist));
        return new ResponseEntity<>(tourist, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/tourists/{id}")
    public synchronized ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        if (id > 0) {
            if (storage.getTouristsMap().containsKey(id)) {
                String seat = storage.getTouristsMap().get(id).getSeat();
                System.out.println("Tourist removed: " + storage.getTouristsMap().remove(id).getName());
                System.out.println("SeatList before: "  + Arrays.toString(storage.getSeatList().toArray()));
                System.out.println("Connected seat to remove: " + seat);
                storage.getSeatList().remove(seat);
                System.out.println("SeatList after: "  + Arrays.toString(storage.getSeatList().toArray()));
            } else {
                return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
            }
        } else {
            storage.getTouristsMap().clear();
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
