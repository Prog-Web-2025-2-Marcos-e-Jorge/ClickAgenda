package br.iff.edu.ccc.clickagenda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.repository.ProfissionalRepository;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Profissional salvar(Profissional profissional) {
        if (usuarioRepository.existsByEmail(profissional.getEmail())) {
            throw new BadRequestException("O e-mail informado já está em uso na plataforma.");
        }

        return profissionalRepository.save(profissional);
    }

    public Profissional buscarPorId(Long id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + id));
    }

    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    @Transactional
    public Profissional atualizar(Long id, Profissional profissionalAtualizado) {
        Profissional profissional = buscarPorId(id);

        if (profissionalAtualizado.getNome() != null) {
            profissional.setNome(profissionalAtualizado.getNome());
        }
        if (profissionalAtualizado.getEmail() != null
                && !profissionalAtualizado.getEmail().equals(profissional.getEmail())) {
            if (usuarioRepository.existsByEmail(profissionalAtualizado.getEmail())) {
                throw new BadRequestException("O e-mail informado já está em uso na plataforma.");
            }
            profissional.setEmail(profissionalAtualizado.getEmail());
        }
        if (profissionalAtualizado.getTelefone() != null) {
            profissional.setTelefone(profissionalAtualizado.getTelefone());
        }
        if (profissionalAtualizado.getEndereco() != null) {
            profissional.setEndereco(profissionalAtualizado.getEndereco());
        }

        return profissionalRepository.save(profissional);
    }

    @Transactional
    public void deletar(Long id) {
        Profissional profissional = buscarPorId(id);
        profissionalRepository.delete(profissional);
    }
}