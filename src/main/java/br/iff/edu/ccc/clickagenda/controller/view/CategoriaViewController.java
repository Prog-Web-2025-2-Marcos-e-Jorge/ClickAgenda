package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iff.edu.ccc.clickagenda.dto.request.CategoriaRequestDTO;
import br.iff.edu.ccc.clickagenda.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/categoria")
public class CategoriaViewController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioCadastro(Model model) {
        log.info("Acessando formulário de cadastro de categoria");
        model.addAttribute("categoria", new CategoriaRequestDTO());
        return "categoria-formulario";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String salvarCategoria(@Valid CategoriaRequestDTO categoria, Model model) {
        try {
            log.info("Tentando salvar categoria: {}", categoria.getNome());
            categoriaService.salvar(categoria);
            log.info("Categoria salva com sucesso: {}", categoria.getNome());
            return "redirect:/categoria/sucesso";
        } catch (Exception e) {
            log.warn("Erro ao salvar categoria: {}", e.getMessage());
            model.addAttribute("categoria", categoria);
            model.addAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "categoria-formulario";
        }
    }

    @GetMapping("/sucesso")
    public String mostrarPaginaSucesso(Model model) {
        log.info("Exibindo página de sucesso de categoria");
        return "categoria-sucesso";
    }
}
