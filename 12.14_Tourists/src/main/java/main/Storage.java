package main;

import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Storage {
    private final int currentId = 1;

    @Autowired
    private TouristRepository touristRepository;

    @Transactional
    public void addTourist (Tourist tourist) {
        boolean indicatorOfDoubledSeat;
        Iterable<Tourist> touristIterable = touristRepository.findAll();
        List<Tourist> touristList = new ArrayList<>();
        touristIterable.forEach(touristList::add);
        indicatorOfDoubledSeat = touristList.stream().anyMatch(t -> t.getSeat().equals(tourist.getSeat()));
        if(indicatorOfDoubledSeat || !tourist.getBirthday().matches("\\d{4}-\\d{2}-\\d{2}") || !tourist.getName().matches("[a-zA-Z]*")) {
            return;
        }
        touristRepository.save(tourist);
    }

    @Transactional
    public Tourist putCorrectives(Integer id, String name, String seatNew, String birthday) {
        Optional<Tourist> optTourist = touristRepository.findById(id);
        if(!optTourist.isPresent()) {
            return null;
        }
        Tourist tourist = optTourist.get();
        String seatExisting = tourist.getSeat();

        if (seatExisting.equals(seatNew.trim())) {
            return null;
        }
        if(name != null) {
            tourist.setName(name);
        }
        if(seatNew != null) {
            tourist.setSeat(seatNew);
        }
        if(birthday != null) {
            tourist.setBirthday(birthday);
        }
        touristRepository.save(tourist);
        return tourist;
    }
}
