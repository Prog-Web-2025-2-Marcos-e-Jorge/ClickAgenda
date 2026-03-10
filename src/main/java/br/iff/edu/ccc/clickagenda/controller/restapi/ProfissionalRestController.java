package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.dto.request.ProfissionalRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ProfissionalResponseDTO;
import br.iff.edu.ccc.clickagenda.service.ProfissionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/profissional")
@RequiredArgsConstructor
public class ProfissionalRestController {

    private final ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<ProfissionalResponseDTO> criarProfissional(@Valid @RequestBody ProfissionalRequestDTO dto) {
        ProfissionalResponseDTO response = profissionalService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorId(@PathVariable Long id) {
        ProfissionalResponseDTO profissional = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissional);
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> listarTodos() {
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody ProfissionalRequestDTO dto) {
        ProfissionalResponseDTO response = profissionalService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}