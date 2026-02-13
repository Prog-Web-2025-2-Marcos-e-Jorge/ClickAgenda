package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {

    @GetMapping
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Cliente Rest Controller!");
    }

}
