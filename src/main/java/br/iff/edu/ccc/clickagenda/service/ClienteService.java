package br.iff.edu.ccc.clickagenda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
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
            throw new BadRequestException("O e-mail informado já está em uso na plataforma.");
        }

        // TODO: adicionar criptografia de senha

        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado com ID: " + id));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarPorId(id);

        if (clienteAtualizado.getNome() != null) {
            cliente.setNome(clienteAtualizado.getNome());
        }
        if (clienteAtualizado.getEmail() != null && !clienteAtualizado.getEmail().equals(cliente.getEmail())) {
            if (usuarioRepository.existsByEmail(clienteAtualizado.getEmail())) {
                throw new BadRequestException("O e-mail informado já está em uso na plataforma.");
            }
            cliente.setEmail(clienteAtualizado.getEmail());
        }
        if (clienteAtualizado.getTelefone() != null) {
            cliente.setTelefone(clienteAtualizado.getTelefone());
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}