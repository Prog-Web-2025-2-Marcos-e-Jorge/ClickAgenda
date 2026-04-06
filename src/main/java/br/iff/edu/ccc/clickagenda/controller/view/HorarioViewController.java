package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iff.edu.ccc.clickagenda.dto.request.HorarioTrabalhoFormDTO;
import br.iff.edu.ccc.clickagenda.dto.request.HorarioTrabalhoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.HorarioTrabalhoResponseDTO;
import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import br.iff.edu.ccc.clickagenda.service.HorarioTrabalhoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequestMapping("/horario")
@RequiredArgsConstructor
public class HorarioViewController {

    private final HorarioTrabalhoService horarioTrabalhoService;

    @GetMapping("/novo")
    public String mostrarFormulario(HttpSession session, Model model) {
        Long profissionalId = (Long) session.getAttribute("usuarioId");

        HorarioTrabalhoFormDTO form = horarioTrabalhoService.carregarHorariosParaFormulario(profissionalId);

        model.addAttribute("horarioForm", form);
        model.addAttribute("diasSemana", DiaSemana.values());
        return "profissional/horario-formulario";
    }

    @PostMapping("/novo")
    public String salvarHorarios(@Valid @ModelAttribute HorarioTrabalhoFormDTO horarioForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            Long profissionalId = (Long) session.getAttribute("usuarioId");
            HorarioTrabalhoFormDTO form = horarioTrabalhoService.carregarHorariosParaFormulario(profissionalId);

            model.addAttribute("horarioForm", form);
            model.addAttribute("diasSemana", DiaSemana.values());
            return "profissional/horario-formulario";
        }

        Long profissionalId = (Long) session.getAttribute("usuarioId");

        List<HorarioTrabalhoResponseDTO> existentes = horarioTrabalhoService.listarPorProfissional(profissionalId);

        try {
            for (HorarioTrabalhoRequestDTO dto : horarioForm.getHorarios()) {
                dto.setProfissionalId(profissionalId);

                existentes.stream()
                        .filter(h -> h.getDiaSemana() == dto.getDiaSemana())
                        .findFirst()
                        .ifPresentOrElse(
                                h -> horarioTrabalhoService.atualizar(h.getId(), dto),
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