package br.iff.edu.ccc.clickagenda.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.ForbiddenException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
import br.iff.edu.ccc.clickagenda.model.Agendamento;
import br.iff.edu.ccc.clickagenda.model.HorarioTrabalho;
import br.iff.edu.ccc.clickagenda.repository.AgendamentoRepository;
import br.iff.edu.ccc.clickagenda.repository.HorarioTrabalhoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final HorarioTrabalhoRepository horarioTrabalhoRepository;

    @Transactional
    public Agendamento agendar(Agendamento agendamento) {
        if (agendamento.getCliente() == null || agendamento.getProfissional() == null
                || agendamento.getServico() == null) {
            throw new BadRequestException(
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

        DiaSemana diaSemanaEnum = converterDayOfWeek(inicioNovo.getDayOfWeek());
        HorarioTrabalho horarioTrabalho = horarioTrabalhoRepository
                .findByProfissionalIdAndDiaSemana(novoAgendamento.getProfissional().getId(), diaSemanaEnum)
                .orElseThrow(() -> new BadRequestException("O profissional não atende neste dia da semana."));

        if (horarioTrabalho.isDiaFolga()) {
            throw new BadRequestException("O profissional está de folga neste dia.");
        }

        if (inicioNovo.toLocalTime().isBefore(horarioTrabalho.getHorarioInicio()) ||
                fimNovo.toLocalTime().isAfter(horarioTrabalho.getHorarioFim())) {
            throw new BadRequestException("O horário solicitado está fora do expediente do profissional.");
        }

        LocalDateTime inicioDia = inicioNovo.toLocalDate().atStartOfDay();
        LocalDateTime fimDia = inicioDia.plusDays(1);

        List<Agendamento> agendamentosDoDia = agendamentoRepository.findAgendamentosDoDia(
                novoAgendamento.getProfissional().getId(), inicioDia, fimDia);

        for (Agendamento existente : agendamentosDoDia) {
            LocalDateTime inicioExistente = existente.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(existente.getServico().getDuracaoMinutos());

            boolean conflito = inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente);

            if (conflito) {
                throw new BadRequestException("O profissional não está disponível neste horário. Conflito detectado.");
            }
        }
    }

    @Transactional
    public void cancelarAgendamento(Long idAgendamento, Long idUsuarioSolicitante, String tipoUsuario) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + idAgendamento));

        if ("CLIENTE".equalsIgnoreCase(tipoUsuario)) {
            if (!agendamento.getCliente().getId().equals(idUsuarioSolicitante)) {
                throw new ForbiddenException("Acesso negado: Um cliente só pode cancelar seus próprios agendamentos.");
            }
        } else if ("PROFISSIONAL".equalsIgnoreCase(tipoUsuario)) {
            if (!agendamento.getProfissional().getId().equals(idUsuarioSolicitante)) {
                throw new ForbiddenException(
                        "Acesso negado: Um profissional só pode cancelar agendamentos de sua própria agenda.");
            }
        } else {
            throw new BadRequestException("Tipo de usuário inválido para cancelamento.");
        }

        agendamentoRepository.delete(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));
    }

    @Transactional
    public Agendamento atualizar(Long id, Agendamento agendamentoAtualizado) {
        Agendamento agendamento = buscarPorId(id);

        if (agendamentoAtualizado.getDataHora() != null) {
            agendamento.setDataHora(agendamentoAtualizado.getDataHora());
        }
        if (agendamentoAtualizado.getObservacoes() != null) {
            agendamento.setObservacoes(agendamentoAtualizado.getObservacoes());
        }
        if (agendamentoAtualizado.getCliente() != null) {
            agendamento.setCliente(agendamentoAtualizado.getCliente());
        }
        if (agendamentoAtualizado.getProfissional() != null) {
            agendamento.setProfissional(agendamentoAtualizado.getProfissional());
        }
        if (agendamentoAtualizado.getServico() != null) {
            agendamento.setServico(agendamentoAtualizado.getServico());
        }

        validarDisponibilidade(agendamento);

        return agendamentoRepository.save(agendamento);
    }

    private DiaSemana converterDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> DiaSemana.SEGUNDA;
            case TUESDAY -> DiaSemana.TERCA;
            case WEDNESDAY -> DiaSemana.QUARTA;
            case THURSDAY -> DiaSemana.QUINTA;
            case FRIDAY -> DiaSemana.SEXTA;
            case SATURDAY -> DiaSemana.SABADO;
            case SUNDAY -> DiaSemana.DOMINGO;
        };
    }
}