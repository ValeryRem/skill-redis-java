package main;

import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
public class TouristController {

    @Autowired
    private TouristRepository touristRepository;

    @GetMapping("/tourists/")
    public List<Tourist> list() {
        return Storage.getTouristsMap();
    }

    @PostMapping("/tourists/")
    public int addTourist (Tourist tourist) {
        Tourist  newTourist = touristRepository.save(tourist);
        Storage.addTourist(tourist);
        return newTourist.getId();
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> get(@PathVariable("id") Integer id) {
        Tourist tourist = Storage.getTourist(id);
//        return ResponseEntity.of(Optional.ofNullable(tourist));
//    }
            if (tourist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    @PutMapping("/tourists/{id}")
    public Tourist putNewData(Integer touristId, String name, int seat, String birthday) {
        Tourist tourist = Storage.getTourist(touristId);
        if(name.length() > 0) {
            Objects.requireNonNull(tourist).setName(name);
        }
        List<Integer> seatList = new ArrayList<>();
        Collection<Tourist> tourists = Storage.touristsMap.values();
        tourists.forEach(x -> seatList.add(x.getSeat()));
        if(seat > 0) {
            if (!seatList.contains(seat)) {
                Objects.requireNonNull(tourist).setSeat(seat);
            } else {
                System.out.println("This seat is occupied. Change seat number!");
            }
        }
        if(birthday.length() >0) {
            Objects.requireNonNull(tourist).setBirthday(birthday);
        }
        touristRepository.save(Objects.requireNonNull(tourist));
        return tourist;
    }
}
