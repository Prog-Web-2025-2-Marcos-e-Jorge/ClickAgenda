package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iff.edu.ccc.clickagenda.model.Usuario;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;

@Controller
@RequestMapping(path = "principal")
public class MainViewController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private br.iff.edu.ccc.clickagenda.service.CategoriaService categoriaService;

    @GetMapping()
    public String index(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (!usuarios.isEmpty()) {
            model.addAttribute("usuario", usuarios.get(0));
        } else {
            model.addAttribute("usuario", new Usuario() {
                @Override
                public String toString() {
                    return "Usuário";
                }
            });
        }
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("profissionaisDestaque", List.of());
        return "index";
    }
}
