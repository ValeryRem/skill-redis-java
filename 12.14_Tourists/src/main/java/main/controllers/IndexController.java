package main.controllers;

import main.Storage;
import main.model.Tourist;
import main.model.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @GetMapping("/index2")
    @ResponseBody
    public String index2() {
        return "index";
    }

    @Value("${someParameter.value}")
    private String someParameter;

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private Storage storage;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Tourist> touristIterable = touristRepository.findAll();
        List<Tourist> touristList = new ArrayList<>();
        touristIterable.forEach(touristList::add);
        model.addAttribute("tourists", touristList);
        model.addAttribute("touristsCount", touristList.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }

    @PostMapping("/")
    public String touristSubmit(@ModelAttribute Tourist tourist, Model model) {
        touristRepository.save(tourist);
        return index(model);
    }
}
