package br.iff.edu.ccc.clickagenda.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.iff.edu.ccc.clickagenda.dto.request.HorarioTrabalhoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.HorarioTrabalhoResponseDTO;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
import br.iff.edu.ccc.clickagenda.model.HorarioTrabalho;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.repository.HorarioTrabalhoRepository;
import br.iff.edu.ccc.clickagenda.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HorarioTrabalhoService {

    private final HorarioTrabalhoRepository horarioTrabalhoRepository;
    private final ProfissionalRepository profissionalRepository;

    @Transactional
    public HorarioTrabalhoResponseDTO salvar(HorarioTrabalhoRequestDTO dto) {
        // Validar se profissional existe
        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(
                        () -> new NotFoundException("Profissional não encontrado com ID: " + dto.getProfissionalId()));

        // Validar se já existe um horário para esse dia da semana
        HorarioTrabalho existente = horarioTrabalhoRepository
                .findByProfissionalIdAndDiaSemana(dto.getProfissionalId(), dto.getDiaSemana())
                .orElse(null);

        if (existente != null) {
            throw new BadRequestException(
                    "Já existe um horário de trabalho cadastrado para " + dto.getDiaSemana() + " neste profissional");
        }

        // Validar horários
        validarHorarios(dto.getHorarioInicio(), dto.getHorarioFim(), dto.isDiaFolga());

        HorarioTrabalho horario = new HorarioTrabalho();
        horario.setDiaSemana(dto.getDiaSemana());
        horario.setHorarioInicio(dto.getHorarioInicio());
        horario.setHorarioFim(dto.getHorarioFim());
        horario.setDiaFolga(dto.isDiaFolga());
        horario.setProfissional(profissional);

        HorarioTrabalho salvo = horarioTrabalhoRepository.save(horario);
        return converterResponseDTO(salvo);
    }

    public HorarioTrabalhoResponseDTO buscarPorId(Long id) {
        HorarioTrabalho horario = horarioTrabalhoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário de trabalho não encontrado com ID: " + id));
        return converterResponseDTO(horario);
    }

    public List<HorarioTrabalhoResponseDTO> listarTodas() {
        return horarioTrabalhoRepository.findAll().stream()
                .map(this::converterResponseDTO)
                .toList();
    }

    public List<HorarioTrabalhoResponseDTO> listarPorProfissional(Long profissionalId) {
        // Validar se profissional existe
        profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + profissionalId));

        return horarioTrabalhoRepository.findByProfissionalIdOrderByDiaSemana(profissionalId).stream()
                .map(this::converterResponseDTO)
                .toList();
    }

    @Transactional
    public HorarioTrabalhoResponseDTO atualizar(Long id, HorarioTrabalhoRequestDTO dto) {
        HorarioTrabalho horario = horarioTrabalhoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário de trabalho não encontrado com ID: " + id));

        // Se mudar de profissional, validar que o novo profissional existe
        if (dto.getProfissionalId() != null && !dto.getProfissionalId().equals(horario.getProfissional().getId())) {
            Profissional novoProfissional = profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new NotFoundException(
                            "Profissional não encontrado com ID: " + dto.getProfissionalId()));

            // Validar se já existe horário para o mesmo dia na nova data
            HorarioTrabalho existente = horarioTrabalhoRepository
                    .findByProfissionalIdAndDiaSemana(dto.getProfissionalId(), dto.getDiaSemana())
                    .orElse(null);

            if (existente != null && !existente.getId().equals(id)) {
                throw new BadRequestException(
                        "Já existe um horário de trabalho cadastrado para " + dto.getDiaSemana()
                                + " neste profissional");
            }

            horario.setProfissional(novoProfissional);
        }

        if (dto.getDiaSemana() != null) {
            // Se mudando dia da semana, validar antes
            HorarioTrabalho existente = horarioTrabalhoRepository
                    .findByProfissionalIdAndDiaSemana(horario.getProfissional().getId(), dto.getDiaSemana())
                    .orElse(null);

            if (existente != null && !existente.getId().equals(id)) {
                throw new BadRequestException(
                        "Já existe um horário de trabalho cadastrado para " + dto.getDiaSemana()
                                + " neste profissional");
            }

            horario.setDiaSemana(dto.getDiaSemana());
        }

        if (dto.getHorarioInicio() != null) {
            horario.setHorarioInicio(dto.getHorarioInicio());
        }

        if (dto.getHorarioFim() != null) {
            horario.setHorarioFim(dto.getHorarioFim());
        }

        // Sempre pode atualizar diaFolga
        horario.setDiaFolga(dto.isDiaFolga());

        // Validar horários após atualizar
        validarHorarios(horario.getHorarioInicio(), horario.getHorarioFim(), horario.isDiaFolga());

        HorarioTrabalho atualizado = horarioTrabalhoRepository.save(horario);
        return converterResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        HorarioTrabalho horario = horarioTrabalhoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário de trabalho não encontrado com ID: " + id));
        horarioTrabalhoRepository.delete(horario);
    }

    /**
     * Valida se os horários de trabalho são coerentes
     */
    private void validarHorarios(LocalTime horarioInicio, LocalTime horarioFim, boolean diaFolga) {
        if (!diaFolga) {
            if (horarioInicio == null || horarioFim == null) {
                throw new BadRequestException("Para dias não-folga, horário de início e fim são obrigatórios");
            }

            if (horarioInicio.isAfter(horarioFim) || horarioInicio.equals(horarioFim)) {
                throw new BadRequestException("Horário de início deve ser anterior ao horário de fim");
            }

            // Validar intervalo mínimo (ex: 15 minutos)
            if (horarioInicio.plusMinutes(15).isAfter(horarioFim)) {
                throw new BadRequestException("Intervalo de trabalho deve ser de no mínimo 15 minutos");
            }
        }
    }

    private HorarioTrabalhoResponseDTO converterResponseDTO(HorarioTrabalho horario) {
        HorarioTrabalhoResponseDTO dto = new HorarioTrabalhoResponseDTO();
        dto.setId(horario.getId());
        dto.setDiaSemana(horario.getDiaSemana());
        dto.setHorarioInicio(horario.getHorarioInicio());
        dto.setHorarioFim(horario.getHorarioFim());
        dto.setDiaFolga(horario.isDiaFolga());
        dto.setProfissionalId(horario.getProfissional().getId());
        dto.setProfissionalNome(horario.getProfissional().getNome());
        return dto;
    }
}
