package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.dto.AgendamentoDTO;
import br.iff.edu.ccc.clickagenda.model.Agendamento;
import br.iff.edu.ccc.clickagenda.model.Cliente;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.model.Servico;
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
    public ResponseEntity<AgendamentoDTO> agendar(@Valid @RequestBody AgendamentoDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setObservacoes(dto.getObservacoes());

        if (dto.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getCliente().getId());
            agendamento.setCliente(cliente);
        }
        if (dto.getProfissional() != null) {
            Profissional prof = new Profissional();
            prof.setId(dto.getProfissional().getId());
            agendamento.setProfissional(prof);
        }
        if (dto.getServico() != null) {
            Servico serv = new Servico();
            serv.setId(dto.getServico().getId());
            agendamento.setServico(serv);
        }

        Agendamento salvo = agendamentoService.agendar(agendamento);

        AgendamentoDTO responseDTO = new AgendamentoDTO();
        responseDTO.setId(salvo.getId());
        responseDTO.setDataHora(salvo.getDataHora());
        responseDTO.setValor(salvo.getValor());
        responseDTO.setObservacoes(salvo.getObservacoes());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoDTO dto) {
        Agendamento agendamentoAtualizado = new Agendamento();
        agendamentoAtualizado.setDataHora(dto.getDataHora());
        agendamentoAtualizado.setObservacoes(dto.getObservacoes());

        if (dto.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getCliente().getId());
            agendamentoAtualizado.setCliente(cliente);
        }
        if (dto.getProfissional() != null) {
            Profissional prof = new Profissional();
            prof.setId(dto.getProfissional().getId());
            agendamentoAtualizado.setProfissional(prof);
        }
        if (dto.getServico() != null) {
            Servico serv = new Servico();
            serv.setId(dto.getServico().getId());
            agendamentoAtualizado.setServico(serv);
        }

        Agendamento atualizado = agendamentoService.atualizar(id, agendamentoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarAgendamento(
            @PathVariable Long id,
            @RequestParam Long idUsuarioSolicitante,
            @RequestParam String tipoUsuario) {

        agendamentoService.cancelarAgendamento(id, idUsuarioSolicitante, tipoUsuario);
        return ResponseEntity.noContent().build();
    }
}