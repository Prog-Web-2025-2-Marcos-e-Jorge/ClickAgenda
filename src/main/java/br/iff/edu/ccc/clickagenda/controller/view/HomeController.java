package br.iff.edu.ccc.clickagenda.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String usuarioEmail = (String) session.getAttribute("usuarioEmail");

        if (usuarioEmail == null) {
            return "redirect:/login";
        }

        Object usuarioNome = session.getAttribute("usuarioNome");
        Object usuarioPerfil = session.getAttribute("usuarioPerfil");

        if (usuarioNome != null) {
            model.addAttribute("usuarioNome", usuarioNome);
        }
        if (usuarioPerfil != null) {
            model.addAttribute("usuarioPerfil", usuarioPerfil);
        }

        model.addAttribute("categorias", java.util.List.of());
        model.addAttribute("profissionaisDestaque", java.util.List.of());

        return "index";
    }

    @GetMapping("/index.html")
    public String indexRedirect() {
        return "redirect:/";
    }
}
