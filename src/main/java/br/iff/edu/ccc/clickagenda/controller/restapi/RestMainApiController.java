package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class RestMainApiController {

    @GetMapping()
    public ResponseEntity<String> getApiHome() {
        return ResponseEntity.ok("Bem vindo à API REST do MultiAgenda!");
    }
}
