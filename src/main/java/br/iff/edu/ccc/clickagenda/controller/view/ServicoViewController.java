package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.List;

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
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/servico")
@RequiredArgsConstructor
public class ServicoViewController {

    private final ServicoService servicoService;
    private final ProfissionalService profissionalService;
    private final CategoriaService categoriaService;

    private void carregarDadosFormulario(Model model) {
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
        List<CategoriaResponseDTO> categorias = categoriaService.listarTodas();
        model.addAttribute("profissionais", profissionais);
        model.addAttribute("categorias", categorias);
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(HttpSession session, Model model) {
        ServicoRequestDTO servico = new ServicoRequestDTO();

        String perfil = (String) session.getAttribute("usuarioPerfil");
        if ("PROFISSIONAL".equals(perfil)) {
            Long profissionalId = (Long) session.getAttribute("usuarioId");
            servico.setProfissionalId(profissionalId);
        }

        model.addAttribute("servico", servico);
        carregarDadosFormulario(model);

        return "servico/servico-formulario";
    }

    @PostMapping
    public String salvarServico(@Valid ServicoRequestDTO servico,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("servico", servico);
            carregarDadosFormulario(model);
            return "servico/servico-formulario";
        }

        try {
            String perfil = (String) session.getAttribute("usuarioPerfil");
            if ("PROFISSIONAL".equals(perfil)) {
                servico.setProfissionalId((Long) session.getAttribute("usuarioId"));
            }

            servicoService.salvar(servico);
            return "redirect:/servico/sucesso";
        } catch (Exception e) {
            model.addAttribute("servico", servico);
            carregarDadosFormulario(model);
            model.addAttribute("erro", "Erro ao salvar: " + e.getMessage());

            return "servico/servico-formulario";
        }
    }

    @GetMapping("/sucesso")
    public String mostrarPaginaSucesso() {
        return "servico/servico-sucesso";
    }

    @GetMapping("/{id}")
    public String getServico(@PathVariable Long id, Model model) {
        try {
            ServicoResponseDTO servico = servicoService.buscarPorId(id);
            model.addAttribute("servico", servico);
            return "servico/servico-detalhes";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}