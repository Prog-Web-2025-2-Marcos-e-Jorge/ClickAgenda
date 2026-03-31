package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import br.iff.edu.ccc.clickagenda.model.Usuario;

@Controller
@RequestMapping(path = "principal")
public class MainViewController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping()
    public String index(Model model) {
        // Buscar o primeiro usuário disponível
        var usuarios = usuarioRepository.findAll();
        if (!usuarios.isEmpty()) {
            model.addAttribute("usuario", usuarios.get(0));
        } else {
            // Se não há usuários, criar um objeto vazio para evitar NullPointerException
            model.addAttribute("usuario", new Usuario() {
                @Override
                public String toString() {
                    return "Usuário";
                }
            });
        }
        return "index.html";
    }
}
