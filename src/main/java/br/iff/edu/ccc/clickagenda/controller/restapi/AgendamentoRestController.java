package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.dto.request.AgendamentoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.AgendamentoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/agendamento")
@RequiredArgsConstructor
public class AgendamentoRestController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> agendar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.agendar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarAgendamento(
            @PathVariable Long id,
            @RequestParam Long idUsuarioSolicitante,
            @RequestParam String tipoUsuario) {
        agendamentoService.cancelarAgendamento(id, idUsuarioSolicitante, tipoUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<AgendamentoResponseDTO> confirmarAgendamento(
            @PathVariable Long id,
            @RequestParam Long idProfissional) {
        AgendamentoResponseDTO response = agendamentoService.confirmarAgendamento(id, idProfissional);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<AgendamentoResponseDTO> recusarAgendamento(
            @PathVariable Long id,
            @RequestParam Long idProfissional) {
        AgendamentoResponseDTO response = agendamentoService.recusarAgendamento(id, idProfissional);
        return ResponseEntity.ok(response);
    }
}