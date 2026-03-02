package br.iff.edu.ccc.clickagenda.controller.restapi;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.model.Categoria;
import br.iff.edu.ccc.clickagenda.service.CategoriaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }
}