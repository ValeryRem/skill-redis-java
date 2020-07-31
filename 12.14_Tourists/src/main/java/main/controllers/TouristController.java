package main.controllers;

import main.Storage;
import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
public class TouristController {
    @Autowired
    Storage storage;

    private final TouristRepository touristRepository;

    public TouristController(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @PostMapping("/tourists/")
    public Tourist addTourist (Tourist tourist) {
       storage.addTourist(tourist);
       return tourist;
    }

    @GetMapping("/tourists/")
    public Iterable<Tourist> list() {
        //        List<Tourist> touristList = new ArrayList<>();
//        touristIterable.forEach(touristList::add);
        return touristRepository.findAll();
    }

    @GetMapping("/tourists/{id}")
    public Tourist getTourist (@PathVariable("id") Integer id) {
        return touristRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such tourist"));
//        Optional<Tourist> optionalTourist = touristRepository.findById(id);
//        return optionalTourist.
//                map(tourist -> new ResponseEntity<>(tourist, HttpStatus.OK)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/tourists/{id}")
    public Tourist putChanges(@PathVariable("id")Integer id, String name, String seat, String birthday) {
        storage.putCorrectives(id, name, seat, birthday);
        return touristRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such id!"));
//        if(!touristRepository.findById(id).isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        return new ResponseEntity<>(storage.putCorrectives(id, name, seat, birthday), HttpStatus.OK);
    }

    @DeleteMapping("/tourists/{id}")
    public ResponseEntity <Tourist> deleteTourist(@PathVariable("id") Integer id) {
        if(!touristRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Tourist tourist = touristRepository.findById(id).get();
        touristRepository.deleteById(id);
        return new ResponseEntity<>(tourist, HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/tourists/")
    public ResponseEntity<String> deleteAll() {
        touristRepository.deleteAll();
        return new ResponseEntity<>("The set of tourists deleted", HttpStatus.MOVED_PERMANENTLY);
    }
}
