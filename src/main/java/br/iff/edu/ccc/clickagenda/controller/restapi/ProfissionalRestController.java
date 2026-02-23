package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profissional")
public class ProfissionalRestController {
    @GetMapping
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Agendamento Rest Controller!");
    }
}
