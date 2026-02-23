package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/home")
public class MainViewController {

    @GetMapping()
    public String home() {
        return "home";
    }
}
