package main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/index2")
    @ResponseBody
    public String index2() {
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("touristsCount", "hello!");
        return "index";
    }
}
