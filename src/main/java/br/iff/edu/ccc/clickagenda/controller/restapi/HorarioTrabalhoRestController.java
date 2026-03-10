package br.iff.edu.ccc.clickagenda.controller.restapi;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.dto.request.HorarioTrabalhoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.HorarioTrabalhoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.HorarioTrabalhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/horario-trabalho")
@RequiredArgsConstructor
public class HorarioTrabalhoRestController {

    private final HorarioTrabalhoService horarioTrabalhoService;

    @PostMapping
    public ResponseEntity<HorarioTrabalhoResponseDTO> criar(@Valid @RequestBody HorarioTrabalhoRequestDTO dto) {
        HorarioTrabalhoResponseDTO response = horarioTrabalhoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HorarioTrabalhoResponseDTO>> listarTodas() {
        List<HorarioTrabalhoResponseDTO> horarios = horarioTrabalhoService.listarTodas();
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioTrabalhoResponseDTO> buscarPorId(@PathVariable Long id) {
        HorarioTrabalhoResponseDTO horario = horarioTrabalhoService.buscarPorId(id);
        return ResponseEntity.ok(horario);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<HorarioTrabalhoResponseDTO>> listarPorProfissional(@PathVariable Long profissionalId) {
        List<HorarioTrabalhoResponseDTO> horarios = horarioTrabalhoService.listarPorProfissional(profissionalId);
        return ResponseEntity.ok(horarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioTrabalhoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody HorarioTrabalhoRequestDTO dto) {
        HorarioTrabalhoResponseDTO response = horarioTrabalhoService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        horarioTrabalhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
