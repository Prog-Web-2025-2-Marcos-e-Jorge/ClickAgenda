package br.iff.edu.ccc.clickagenda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            throw new IllegalArgumentException("O e-mail informado já está em uso na plataforma.");
        }

        return profissionalRepository.save(profissional);
    }

    public Profissional buscarPorId(Long id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado."));
    }
}