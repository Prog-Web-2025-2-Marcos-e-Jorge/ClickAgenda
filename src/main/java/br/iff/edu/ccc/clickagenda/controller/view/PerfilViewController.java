package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.iff.edu.ccc.clickagenda.dto.response.AgendamentoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.AgendamentoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PerfilViewController {

    private final AgendamentoService agendamentoService;

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        String perfil = (String) session.getAttribute("usuarioPerfil");

        if ("PROFISSIONAL".equals(perfil)) {
            // Profissional vê todos os agendamentos dele
            List<AgendamentoResponseDTO> todos = agendamentoService.listarPorProfissional(usuarioId);

            // Separa pendentes dos demais
            model.addAttribute("agendamentosPendentes", todos.stream()
                    .filter(a -> a.getStatus().name().equals("PENDENTE"))
                    .toList());

            model.addAttribute("agendamentosConfirmados", todos.stream()
                    .filter(a -> a.getStatus().name().equals("CONFIRMADO"))
                    .toList());

            model.addAttribute("agendamentosRecusados", todos.stream()
                    .filter(a -> a.getStatus().name().equals("CANCELADO"))
                    .toList());
        } else if ("CLIENTE".equals(perfil)) {
            model.addAttribute("agendamentos",
                    agendamentoService.listarPorCliente(usuarioId));
        } else {
            model.addAttribute("agendamentos", List.of());
        }

        return "perfil";
    }
}