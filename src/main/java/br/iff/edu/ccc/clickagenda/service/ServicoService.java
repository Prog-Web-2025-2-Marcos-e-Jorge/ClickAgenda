package br.iff.edu.ccc.clickagenda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.model.Servico;
import br.iff.edu.ccc.clickagenda.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;

    @Transactional
    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }
}