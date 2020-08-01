package main.controllers;

import main.GlobalExceptionHandler;
import main.Storage;
import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/tourists")
public class TouristController {
    @Autowired
    private Storage storage;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private final TouristRepository touristRepository;

    public TouristController(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @PostMapping("/")
    public Tourist addTourist (Tourist tourist) {
       storage.addTourist(tourist);
       return tourist;
    }

    @GetMapping("/")
    public Iterable<Tourist> list() {
        //        List<Tourist> touristList = new ArrayList<>();
//        touristIterable.forEach(touristList::add);
        return touristRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tourist getTourist (@PathVariable("id") Integer id) {
        return touristRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//        Optional<Tourist> optionalTourist = touristRepository.findById(id);
//        return optionalTourist.
//                map(tourist -> new ResponseEntity<>(tourist, HttpStatus.OK)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public Tourist putChanges(@PathVariable("id")Integer id, String name, String seat, String birthday) throws NoSuchFieldException {
        storage.putCorrectives(id, name, seat, birthday);
        return touristRepository.findById(id).orElseThrow(NoSuchFieldException::new);
//        if(!touristRepository.findById(id).isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        return new ResponseEntity<>(storage.putCorrectives(id, name, seat, birthday), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Tourist> deleteTourist(@PathVariable("id") Integer id) {
        Tourist tourist = touristRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        touristRepository.deleteById(id);
        return new ResponseEntity<>(tourist, HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll() {
        touristRepository.deleteAll();
        return new ResponseEntity<>("The full set of tourists deleted", HttpStatus.MOVED_PERMANENTLY);
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<List<String>> handleContentNotAllowedException(EntityNotFoundException unfe) {
//        List<String> errors = Collections.singletonList(unfe.getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
//    }
}
