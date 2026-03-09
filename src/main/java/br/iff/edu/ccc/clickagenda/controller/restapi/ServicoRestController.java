package br.iff.edu.ccc.clickagenda.controller.restapi;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.dto.ServicoDTO;
import br.iff.edu.ccc.clickagenda.model.Servico;
import br.iff.edu.ccc.clickagenda.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servico")
@RequiredArgsConstructor
public class ServicoRestController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> criarServico(@Valid @RequestBody ServicoDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setValor(dto.getValor());
        servico.setDuracaoMinutos(dto.getDuracaoMinutos());

        Servico salvo = servicoService.salvar(servico);

        ServicoDTO responseDTO = new ServicoDTO();
        responseDTO.setId(salvo.getId());
        responseDTO.setNome(salvo.getNome());
        responseDTO.setValor(salvo.getValor());
        responseDTO.setDuracaoMinutos(salvo.getDuracaoMinutos());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        List<Servico> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        Servico servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @Valid @RequestBody ServicoDTO dto) {
        Servico servicoAtualizado = new Servico();
        servicoAtualizado.setNome(dto.getNome());
        servicoAtualizado.setValor(dto.getValor());
        servicoAtualizado.setDuracaoMinutos(dto.getDuracaoMinutos());

        Servico atualizado = servicoService.atualizar(id, servicoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
