package main;

import main.model.Tourist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TouristController {

    @GetMapping("/tourists/")
    public List<Tourist> list() {
        return Storage.getTourists();
    }

    @PostMapping("/tourists/")
    public int addTourist (Tourist tourist) {
        return Storage.addTourist(tourist);
    }

    @GetMapping("/tourists/{id}")
    public ResponseEntity<Tourist> get(@PathVariable int id) {
        Tourist tourist = Storage.getTourist(id);
        if (tourist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<Tourist>(tourist, HttpStatus.OK);
    }
}
