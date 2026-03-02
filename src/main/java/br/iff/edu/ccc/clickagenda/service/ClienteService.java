package br.iff.edu.ccc.clickagenda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.model.Cliente;
import br.iff.edu.ccc.clickagenda.repository.ClienteRepository;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        // Implementação da RN01: Unicidade de E-mail
        if (usuarioRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("O e-mail informado já está em uso na plataforma.");
        }

        // TODO: adicionar criptografia de senha

        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }
}