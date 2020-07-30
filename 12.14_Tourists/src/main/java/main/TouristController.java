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
import java.util.Optional;

@RestController
public class TouristController {
    @Autowired
    Storage storage;
    @Autowired
    private TouristRepository touristRepository;

    @PostMapping("/tourists/")
    public ResponseEntity<Tourist> addTourist (Tourist tourist) {
       storage.addTourist(tourist);
       return new ResponseEntity<>(tourist, HttpStatus.OK);
    }

    @GetMapping("/tourists/")
    public Collection<Tourist> list() {
        Iterable<Tourist> touristIterable = touristRepository.findAll();
        List<Tourist> touristList = new ArrayList<>();
        touristIterable.forEach(touristList::add);
        return touristList;
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> getTourist (@PathVariable("id") Integer id) {
        Optional<Tourist> optionalTourist = touristRepository.findById(id);
        return optionalTourist.
                map(tourist -> new ResponseEntity<>(tourist, HttpStatus.OK)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/tourists/{id}")
    public ResponseEntity<Tourist> putChanges(@PathVariable("id")Integer id, String name, String seat, String birthday) {
        if(!touristRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(storage.putCorrectives(id, name, seat, birthday), HttpStatus.OK);
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
