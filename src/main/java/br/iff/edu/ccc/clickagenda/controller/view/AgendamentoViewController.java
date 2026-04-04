package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iff.edu.ccc.clickagenda.dto.request.AgendamentoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.AgendamentoService;
import br.iff.edu.ccc.clickagenda.service.ServicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoViewController {

    private final AgendamentoService agendamentoService;
    private final ServicoService servicoService;

    @GetMapping("/novo")
    public String getNovo(@RequestParam Long servicoId, HttpSession session, Model model) {
        ServicoResponseDTO servico = servicoService.buscarPorId(servicoId);
        Long clienteId = (Long) session.getAttribute("usuarioId");
        model.addAttribute("servico", servico);
        model.addAttribute("clienteId", clienteId);

        return "agendamento";
    }

    @PostMapping("/novo")
    public String agendar(@Valid AgendamentoRequestDTO agendamentoDto,
            @RequestParam Long servicoId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.agendar(agendamentoDto);
            redirectAttributes.addFlashAttribute("sucesso", "Agendamento realizado com sucesso!");
            return "redirect:/perfil";
        } catch (Exception e) {
            ServicoResponseDTO servico = servicoService.buscarPorId(servicoId);
            model.addAttribute("servico", servico);
            model.addAttribute("clienteId", session.getAttribute("usuarioId"));
            model.addAttribute("erro", e.getMessage());
            return "agendamento";
        }
    }

}
