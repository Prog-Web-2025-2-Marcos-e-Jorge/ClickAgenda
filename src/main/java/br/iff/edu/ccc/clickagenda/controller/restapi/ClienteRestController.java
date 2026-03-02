package br.iff.edu.ccc.clickagenda.controller.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.iff.edu.ccc.clickagenda.dto.ClienteDTO;
import br.iff.edu.ccc.clickagenda.model.Cliente;
import br.iff.edu.ccc.clickagenda.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteRestController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO dto) {
        Cliente cliente = new Cliente(dto.getNome(), dto.getEmail(), dto.getTelefone(), dto.getSenha(), dto.getCpf());

        Cliente salvo = clienteService.salvar(cliente);

        ClienteDTO responseDTO = converterParaDTO(salvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(converterParaDTO(cliente));
    }

    private ClienteDTO converterParaDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }
}