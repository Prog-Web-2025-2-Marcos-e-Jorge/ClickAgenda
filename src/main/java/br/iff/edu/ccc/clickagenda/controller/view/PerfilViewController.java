package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        if ("CLIENTE".equals(perfil)) {
            model.addAttribute("agendamentos",
                    agendamentoService.listarPorCliente(usuarioId));
        } else {
            model.addAttribute("agendamentos", java.util.List.of());
        }

        return "perfil";
    }
}