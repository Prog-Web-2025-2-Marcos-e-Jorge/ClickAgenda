package br.iff.edu.ccc.clickagenda.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.iff.edu.ccc.clickagenda.model.Agendamento;
import br.iff.edu.ccc.clickagenda.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public Agendamento agendar(Agendamento agendamento) {
        if (agendamento.getCliente() == null || agendamento.getProfissional() == null
                || agendamento.getServico() == null) {
            throw new IllegalArgumentException(
                    "Todo agendamento deve estar vinculado a um Cliente, um Profissional e um Serviço.");
        }

        if (agendamento.getValor() == null) {
            agendamento.setValor(agendamento.getServico().getValor());
        }

        validarDisponibilidade(agendamento);

        return agendamentoRepository.save(agendamento);
    }

    private void validarDisponibilidade(Agendamento novoAgendamento) {
        LocalDateTime inicioNovo = novoAgendamento.getDataHora();
        LocalDateTime fimNovo = inicioNovo.plusMinutes(novoAgendamento.getServico().getDuracaoMinutos());

        List<Agendamento> agendamentosExistentes = agendamentoRepository
                .findByProfissionalId(novoAgendamento.getProfissional().getId());

        for (Agendamento existente : agendamentosExistentes) {
            LocalDateTime inicioExistente = existente.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(existente.getServico().getDuracaoMinutos());

            boolean conflito = inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente);

            if (conflito) {
                throw new RuntimeException("O profissional não está disponível neste horário. Conflito detectado.");
            }
        }
    }

    @Transactional
    public void cancelarAgendamento(Long idAgendamento, Long idUsuarioSolicitante, String tipoUsuario) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));

        if ("CLIENTE".equalsIgnoreCase(tipoUsuario)) {
            if (!agendamento.getCliente().getId().equals(idUsuarioSolicitante)) {
                throw new SecurityException("Acesso negado: Um cliente só pode cancelar seus próprios agendamentos.");
            }
        } else if ("PROFISSIONAL".equalsIgnoreCase(tipoUsuario)) {
            if (!agendamento.getProfissional().getId().equals(idUsuarioSolicitante)) {
                throw new SecurityException(
                        "Acesso negado: Um profissional só pode cancelar agendamentos de sua própria agenda.");
            }
        } else {
            throw new IllegalArgumentException("Tipo de usuário inválido para cancelamento.");
        }

        agendamentoRepository.delete(agendamento);
    }
}