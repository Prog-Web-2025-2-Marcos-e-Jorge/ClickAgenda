package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iff.edu.ccc.clickagenda.dto.request.HorarioTrabalhoFormDTO;
import br.iff.edu.ccc.clickagenda.dto.request.HorarioTrabalhoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.HorarioTrabalhoResponseDTO;
import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import br.iff.edu.ccc.clickagenda.service.HorarioTrabalhoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/horario")
@RequiredArgsConstructor
public class HorarioViewController {

    private final HorarioTrabalhoService horarioTrabalhoService;

    @GetMapping("/novo")
    public String mostrarFormulario(HttpSession session, Model model) {
        Long profissionalId = (Long) session.getAttribute("usuarioId");

        // Busca horários já cadastrados
        List<HorarioTrabalhoResponseDTO> horariosExistentes = horarioTrabalhoService
                .listarPorProfissional(profissionalId);

        // Monta um DTO para cada dia da semana
        List<HorarioTrabalhoRequestDTO> horarios = new ArrayList<>();
        for (DiaSemana dia : DiaSemana.values()) {

            HorarioTrabalhoRequestDTO dto = new HorarioTrabalhoRequestDTO();
            dto.setDiaSemana(dia);
            dto.setProfissionalId(profissionalId);

            // Se já existe horário cadastrado para esse dia, pré-preenche
            horariosExistentes.stream()
                    .filter(h -> h.getDiaSemana() == dia)
                    .findFirst()
                    .ifPresent(h -> {
                        dto.setHorarioInicio(h.getHorarioInicio());
                        dto.setHorarioFim(h.getHorarioFim());
                        dto.setDiaFolga(h.isDiaFolga());
                    });

            horarios.add(dto);
        }

        HorarioTrabalhoFormDTO form = new HorarioTrabalhoFormDTO();
        form.setHorarios(horarios);

        model.addAttribute("horarioForm", form);
        model.addAttribute("diasSemana", DiaSemana.values());
        return "horario-formulario";
    }

    @PostMapping("/novo")
    public String salvarHorarios(HorarioTrabalhoFormDTO horarioForm,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Long profissionalId = (Long) session.getAttribute("usuarioId");

        // Busca horários já existentes para deletar e recriar
        List<HorarioTrabalhoResponseDTO> existentes = horarioTrabalhoService.listarPorProfissional(profissionalId);

        try {
            for (HorarioTrabalhoRequestDTO dto : horarioForm.getHorarios()) {
                dto.setProfissionalId(profissionalId);

                // Verifica se já existe para esse dia
                existentes.stream()
                        .filter(h -> h.getDiaSemana() == dto.getDiaSemana())
                        .findFirst()
                        .ifPresentOrElse(
                                // Se existe, atualiza
                                h -> horarioTrabalhoService.atualizar(h.getId(), dto),
                                // Se não existe e não é dia de folga vazio, cria
                                () -> {
                                    if (dto.isDiaFolga() ||
                                            (dto.getHorarioInicio() != null && dto.getHorarioFim() != null)) {
                                        horarioTrabalhoService.salvar(dto);
                                    }
                                });
            }

            redirectAttributes.addFlashAttribute("sucesso",
                    "Horários de trabalho salvos com sucesso!");
            return "redirect:/perfil";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/horario/novo";
        }
    }
}