package main;

import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TouristController {

    @Autowired
    private TouristRepository touristRepository;

    @GetMapping("/tourists/")
    public List<Tourist> list() {
        return Storage.getTourists();
    }

    @PostMapping("/tourists/")
    public int addTourist (Tourist tourist) {
        Tourist newTourist = touristRepository.save(tourist);
        return newTourist.getId();
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> get(@PathVariable("id") int id) {
        Tourist tourist = Storage.getTourist(id);
//        return ResponseEntity.of(Optional.ofNullable(tourist));
//    }
            if (tourist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(tourist, HttpStatus.OK);
    }
}
