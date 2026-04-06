package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iff.edu.ccc.clickagenda.dto.request.ServicoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.CategoriaResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ProfissionalResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.CategoriaService;
import br.iff.edu.ccc.clickagenda.service.ProfissionalService;
import br.iff.edu.ccc.clickagenda.service.ServicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/servico")
public class ServicoViewController {

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(HttpSession session, Model model) {
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
        List<CategoriaResponseDTO> categorias = categoriaService.listarTodas();

        ServicoRequestDTO servico = new ServicoRequestDTO();

        String perfil = (String) session.getAttribute("usuarioPerfil");
        if ("PROFISSIONAL".equals(perfil)) {
            Long profissionalId = (Long) session.getAttribute("usuarioId");
            servico.setProfissionalId(profissionalId);
        }

        model.addAttribute("servico", servico);
        model.addAttribute("profissionais", profissionais);
        model.addAttribute("categorias", categorias);

        return "servico-formulario";
    }

    @PostMapping
    public String salvarServico(@Valid ServicoRequestDTO servico,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "servico-formulario";
        }

        try {
            // Se for profissional, garante que o ID dele está no DTO
            String perfil = (String) session.getAttribute("usuarioPerfil");
            if ("PROFISSIONAL".equals(perfil)) {
                servico.setProfissionalId((Long) session.getAttribute("usuarioId"));
            }

            servicoService.salvar(servico);
            return "redirect:/servico/sucesso";
        } catch (Exception e) {
            List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
            List<CategoriaResponseDTO> categorias = categoriaService.listarTodas();

            model.addAttribute("servico", servico);
            model.addAttribute("profissionais", profissionais);
            model.addAttribute("categorias", categorias);
            model.addAttribute("erro", "Erro ao salvar: " + e.getMessage());

            return "servico-formulario";
        }
    }

    @GetMapping("/sucesso")
    public String mostrarPaginaSucesso() {
        return "servico-sucesso";
    }

    @GetMapping("/{id}")
    public String getServico(@PathVariable Long id, Model model) {
        try {
            ServicoResponseDTO servico = servicoService.buscarPorId(id);
            model.addAttribute("servico", servico);
            return "servico-detalhes";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}