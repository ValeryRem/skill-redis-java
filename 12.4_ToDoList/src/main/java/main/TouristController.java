package main;

import main.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

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
    public ResponseEntity<Tourist> getTourist (@PathVariable("id") Integer id) {
        return ResponseEntity.of(Optional.of(storage.getTourist(id)));
        //return new ResponseEntity<Tourist>(storage.getTourist(id), HttpStatus.OK);
    }

    @PutMapping("/tourists/{id}")
    public synchronized ResponseEntity<Tourist> putChanges(@PathVariable("id")Integer id, String name, String seat, String birthday) {
        return new ResponseEntity<>(storage.putCorrectives(id, name, seat, birthday), HttpStatus.ACCEPTED);//storage.putNewData(id, name, seat, birthday);
    }

    @DeleteMapping("/tourists/{id}")
    public ResponseEntity<Integer> deleteTourist(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(storage.delete(id), HttpStatus.OK);
    }
}
