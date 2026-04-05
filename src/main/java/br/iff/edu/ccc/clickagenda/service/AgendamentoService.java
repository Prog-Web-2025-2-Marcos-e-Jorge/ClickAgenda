package br.iff.edu.ccc.clickagenda.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.iff.edu.ccc.clickagenda.dto.request.AgendamentoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.AgendamentoResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ClienteResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ProfissionalResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import br.iff.edu.ccc.clickagenda.enums.Status;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.ForbiddenException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
import br.iff.edu.ccc.clickagenda.model.Agendamento;
import br.iff.edu.ccc.clickagenda.model.Cliente;
import br.iff.edu.ccc.clickagenda.model.HorarioTrabalho;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.model.Servico;
import br.iff.edu.ccc.clickagenda.repository.AgendamentoRepository;
import br.iff.edu.ccc.clickagenda.repository.ClienteRepository;
import br.iff.edu.ccc.clickagenda.repository.HorarioTrabalhoRepository;
import br.iff.edu.ccc.clickagenda.repository.ProfissionalRepository;
import br.iff.edu.ccc.clickagenda.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final HorarioTrabalhoRepository horarioTrabalhoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    @Transactional
    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO agendamentoRequest) {
        Profissional profissional = profissionalRepository.findById(agendamentoRequest.getProfissionalId())
                .orElseThrow(() -> new NotFoundException(
                        "Profissional não encontrado com o ID: " + agendamentoRequest.getProfissionalId()));

        Cliente cliente = clienteRepository.findById(agendamentoRequest.getClienteId())
                .orElseThrow(() -> new NotFoundException(
                        "Cliente não encontrado com o ID: " + agendamentoRequest.getClienteId()));

        Servico servico = servicoRepository.findById(agendamentoRequest.getServicoId())
                .orElseThrow(() -> new NotFoundException(
                        "Serviço não encontrado com o ID: " + agendamentoRequest.getServicoId()));

        Agendamento agendamento = new Agendamento();
        agendamento.setProfissional(profissional);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setDataHora(agendamentoRequest.getDataHora());
        agendamento.setObservacoes(agendamentoRequest.getObs());
        agendamento.setValor(agendamentoRequest.getValor());

        validarDisponibilidade(agendamento);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return converterResponseDTO(agendamentoSalvo);
    }

    private void validarDisponibilidade(Agendamento novoAgendamento) {
        validarDisponibilidade(novoAgendamento, null);
    }

    private void validarDisponibilidade(Agendamento novoAgendamento, Long idAgendamentoExistente) {
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
            // Exclui o próprio agendamento que está sendo atualizado da validação
            if (idAgendamentoExistente != null && existente.getId().equals(idAgendamentoExistente)) {
                continue;
            }

            LocalDateTime inicioExistente = existente.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(existente.getServico().getDuracaoMinutos());

            boolean conflito = inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente);

            if (conflito) {
                throw new BadRequestException("O profissional não está disponível neste horário. Conflito detectado.");
            }
        }
    }

    @Transactional
    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO agendamentoRequest) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        if (agendamentoRequest.getProfissionalId() != null) {
            Profissional profissional = profissionalRepository.findById(agendamentoRequest.getProfissionalId())
                    .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));
            agendamento.setProfissional(profissional);
        }

        if (agendamentoRequest.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(agendamentoRequest.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            agendamento.setCliente(cliente);
        }

        if (agendamentoRequest.getServicoId() != null) {
            Servico servico = servicoRepository.findById(agendamentoRequest.getServicoId())
                    .orElseThrow(() -> new NotFoundException("Serviço não encontrado"));
            agendamento.setServico(servico);
        }

        if (agendamentoRequest.getDataHora() != null) {
            agendamento.setDataHora(agendamentoRequest.getDataHora());
        }

        if (agendamentoRequest.getObs() != null) {
            agendamento.setObservacoes(agendamentoRequest.getObs());
        }

        if (agendamentoRequest.getValor() != null) {
            agendamento.setValor(agendamentoRequest.getValor());
        }

        validarDisponibilidade(agendamento, id);

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
        return converterResponseDTO(agendamentoAtualizado);
    }

    @Transactional
    public AgendamentoResponseDTO confirmarAgendamento(Long idAgendamento, Long idProfissional) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + idAgendamento));

        if (!agendamento.getProfissional().getId().equals(idProfissional)) {
            throw new ForbiddenException(
                    "Acesso negado: Um profissional só pode confirmar agendamentos de sua própria agenda.");
        }

        if (!agendamento.getStatus().equals(Status.PENDENTE)) {
            throw new BadRequestException(
                    "Apenas agendamentos pendentes podem ser confirmados. Status atual: " + agendamento.getStatus());
        }

        agendamento.setStatus(Status.CONFIRMADO);
        Agendamento agendamentoConfirmado = agendamentoRepository.save(agendamento);

        return converterResponseDTO(agendamentoConfirmado);
    }

    @Transactional
    public AgendamentoResponseDTO recusarAgendamento(Long idAgendamento, Long idProfissional) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + idAgendamento));

        if (!agendamento.getProfissional().getId().equals(idProfissional)) {
            throw new ForbiddenException(
                    "Acesso negado: Um profissional só pode recusar agendamentos de sua própria agenda.");
        }

        if (!agendamento.getStatus().equals(Status.PENDENTE)) {
            throw new BadRequestException(
                    "Apenas agendamentos pendentes podem ser recusados. Status atual: " + agendamento.getStatus());
        }

        agendamento.setStatus(Status.CANCELADO);
        Agendamento agendamentoRecusado = agendamentoRepository.save(agendamento);

        return converterResponseDTO(agendamentoRecusado);
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

    public List<AgendamentoResponseDTO> listarPorCliente(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId).stream()
                .map(this::converterResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::converterResponseDTO)
                .toList();
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));
        return converterResponseDTO(agendamento);
    }

    public List<AgendamentoResponseDTO> listarPorProfissional(Long profissionalId) {
        return agendamentoRepository.findByProfissionalId(profissionalId).stream()
                .map(this::converterResponseDTO)
                .toList();
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

    private AgendamentoResponseDTO converterResponseDTO(Agendamento agendamento) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setId(agendamento.getId());
        dto.setDataHora(agendamento.getDataHora());
        dto.setObs(agendamento.getObservacoes());
        dto.setValor(agendamento.getValor());
        dto.setStatus(agendamento.getStatus());

        if (agendamento.getProfissional() != null) {
            dto.setProfissional(converterProfissionalResponseDTO(agendamento.getProfissional()));
        }

        if (agendamento.getCliente() != null) {
            dto.setCliente(converterClienteResponseDTO(agendamento.getCliente()));
        }

        if (agendamento.getServico() != null) {
            dto.setServico(converterServicoResponseDTO(agendamento.getServico()));
        }

        return dto;
    }

    private ProfissionalResponseDTO converterProfissionalResponseDTO(Profissional profissional) {
        ProfissionalResponseDTO dto = new ProfissionalResponseDTO();
        dto.setId(profissional.getId());
        dto.setNome(profissional.getNome());
        dto.setCpf(profissional.getCpf());
        dto.setEmail(profissional.getEmail());
        dto.setTelefone(profissional.getTelefone());
        return dto;
    }

    private ClienteResponseDTO converterClienteResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }

    private ServicoResponseDTO converterServicoResponseDTO(Servico servico) {
        ServicoResponseDTO dto = new ServicoResponseDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setValor(servico.getValor());
        dto.setDuracaoMinutos(servico.getDuracaoMinutos());
        return dto;
    }
}