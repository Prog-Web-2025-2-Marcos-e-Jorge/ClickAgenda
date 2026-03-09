package br.iff.edu.ccc.clickagenda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
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

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado com ID: " + id));
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    @Transactional
    public Servico atualizar(Long id, Servico servicoAtualizado) {
        Servico servico = buscarPorId(id);

        if (servicoAtualizado.getNome() != null) {
            servico.setNome(servicoAtualizado.getNome());
        }
        if (servicoAtualizado.getValor() != null) {
            servico.setValor(servicoAtualizado.getValor());
        }
        if (servicoAtualizado.getDuracaoMinutos() != null) {
            servico.setDuracaoMinutos(servicoAtualizado.getDuracaoMinutos());
        }
        if (servicoAtualizado.getCategoria() != null) {
            servico.setCategoria(servicoAtualizado.getCategoria());
        }

        return servicoRepository.save(servico);
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = buscarPorId(id);
        servicoRepository.delete(servico);
    }
}