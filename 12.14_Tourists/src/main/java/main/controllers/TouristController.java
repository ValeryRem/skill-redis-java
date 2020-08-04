package main.controllers;

import main.Storage;
import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tourists")
public class TouristController {
    @Autowired
    private Storage storage;
//    @Autowired
//    private GlobalExceptionHandler globalExceptionHandler;

    private final TouristRepository touristRepository;
    public TouristController(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @GetMapping("/")
    public Iterable<Tourist> list() {
        Iterable<Tourist> tourists = touristRepository.findAll();
        return tourists;
    }

    @PostMapping("/")
    public Tourist addTourist (Tourist tourist) {
       storage.addTourist(tourist);
       return tourist;
    }

    @GetMapping("/{id}")
    public Tourist getTourist (@PathVariable("id") Integer id) {
        return touristRepository.findById(id).get();//.orElseThrow(() -> new EntityNotFoundException("No such tourist to get!"));
    }

    @PutMapping("/{id}")
    public Tourist putChanges(@PathVariable("id")Integer id, String name, String seat, String birthday) throws NoSuchFieldException {
        storage.putCorrectives(id, name, seat, birthday);
        return touristRepository.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Tourist> deleteTourist(@PathVariable("id") Integer id) {
        Tourist tourist = touristRepository.findById(id).get();//.orElseThrow(() -> new EntityNotFoundException("No such tourist to delete!"));
        touristRepository.deleteById(id);
        return new ResponseEntity<>(tourist, HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll() {
        touristRepository.deleteAll();
        return new ResponseEntity<>("The full set of tourists deleted", HttpStatus.MOVED_PERMANENTLY);
    }
}
