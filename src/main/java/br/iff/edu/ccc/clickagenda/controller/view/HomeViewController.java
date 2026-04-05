package br.iff.edu.ccc.clickagenda.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import br.iff.edu.ccc.clickagenda.service.CategoriaService;
import br.iff.edu.ccc.clickagenda.service.ProfissionalService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeViewController {

    private final CategoriaService categoriaService;
    private final ProfissionalService profissionalService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String usuarioEmail = (String) session.getAttribute("usuarioEmail");

        if (usuarioEmail == null) {
            return "redirect:/login";
        }

        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("profissionaisDestaque", profissionalService.listarTodos());

        return "index";
    }

    @GetMapping("/index.html")
    public String indexRedirect() {
        return "redirect:/";
    }
}