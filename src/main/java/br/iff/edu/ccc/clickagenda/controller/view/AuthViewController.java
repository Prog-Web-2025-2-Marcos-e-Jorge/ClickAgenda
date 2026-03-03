package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class AuthViewController {
    @GetMapping
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Autenticação View Controller!");
    }
}
