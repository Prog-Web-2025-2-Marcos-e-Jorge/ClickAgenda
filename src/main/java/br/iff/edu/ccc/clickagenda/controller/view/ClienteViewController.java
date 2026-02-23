package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteViewController {

    @GetMapping
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Cliente View Controller!");
    }

}