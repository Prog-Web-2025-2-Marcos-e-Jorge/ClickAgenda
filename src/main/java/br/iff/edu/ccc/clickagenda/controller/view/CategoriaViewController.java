package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categoria")
public class CategoriaViewController {
    @GetMapping
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Categoria View Controller!");
    }
}
