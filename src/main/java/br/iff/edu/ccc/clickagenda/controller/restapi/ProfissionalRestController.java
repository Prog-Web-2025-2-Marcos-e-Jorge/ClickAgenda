package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.service.ProfissionalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profissional")
@RequiredArgsConstructor
public class ProfissionalRestController {

    private final ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<Profissional> criarProfissional(@RequestBody Profissional profissional) {
        Profissional novoProfissional = profissionalService.salvar(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfissional);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        Profissional profissional = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissional);
    }
}