package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iff.edu.ccc.clickagenda.dto.request.AgendamentoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.AgendamentoResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.repository.ProfissionalRepository;
import br.iff.edu.ccc.clickagenda.service.AgendamentoService;
import br.iff.edu.ccc.clickagenda.service.ServicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoViewController {

    @Autowired
    private ProfissionalRepository profissionalRepository;
    private final AgendamentoService agendamentoService;
    private final ServicoService servicoService;

    @GetMapping("/novo")
    public String getNovo(@RequestParam Long servicoId, HttpSession session, Model model) {
        ServicoResponseDTO servico = servicoService.buscarPorId(servicoId);
        Long clienteId = (Long) session.getAttribute("usuarioId");
        model.addAttribute("servico", servico);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("agendamento", new AgendamentoRequestDTO());

        return "agendamento/agendamento";
    }

    @PostMapping("/novo")
    public String agendar(@Valid AgendamentoRequestDTO agendamento,
            BindingResult bindingResult,
            @RequestParam Long servicoId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            ServicoResponseDTO servico = servicoService.buscarPorId(servicoId);
            model.addAttribute("servico", servico);
            model.addAttribute("clienteId", session.getAttribute("usuarioId"));
            model.addAttribute("agendamento", agendamento);
            return "agendamento/agendamento";
        }

        try {
            agendamentoService.agendar(agendamento);
            redirectAttributes.addFlashAttribute("sucesso", "Agendamento realizado com sucesso!");
            return "redirect:/perfil";
        } catch (Exception e) {
            ServicoResponseDTO servico = servicoService.buscarPorId(servicoId);
            model.addAttribute("servico", servico);
            model.addAttribute("clienteId", session.getAttribute("usuarioId"));
            model.addAttribute("agendamento", agendamento);
            model.addAttribute("erro", e.getMessage());
            return "agendamento/agendamento";
        }
    }

    @GetMapping("/novo/{id}")
    public String exibirFormulario(@PathVariable Long id, org.springframework.ui.Model model) {
        var profissional = profissionalRepository.findById(id).orElse(null);

        if (profissional != null) {
            model.addAttribute("profissional", profissional);
            model.addAttribute("servicos", profissional.getServicos());
        }

        return "agendamento/agendamento";
    }

    @GetMapping("/{id}")
    public String verDetalhe(@PathVariable Long id,
            HttpSession session,
            Model model) {
        AgendamentoResponseDTO agendamento = agendamentoService.buscarPorId(id);
        model.addAttribute("agendamento", agendamento);
        return "agendamento/agendamento-detalhe";
    }

    @PostMapping("/{id}/confirmar")
    public String confirmar(@PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Long profissionalId = (Long) session.getAttribute("usuarioId");
            agendamentoService.confirmarAgendamento(id, profissionalId);
            redirectAttributes.addFlashAttribute("sucesso",
                    "Agendamento confirmado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/perfil";
    }

    @PostMapping("/{id}/recusar")
    public String recusar(@PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Long profissionalId = (Long) session.getAttribute("usuarioId");
            agendamentoService.recusarAgendamento(id, profissionalId);
            redirectAttributes.addFlashAttribute("sucesso",
                    "Agendamento recusado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/perfil";
    }

}
