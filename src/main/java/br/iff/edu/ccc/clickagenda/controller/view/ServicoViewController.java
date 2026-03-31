package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iff.edu.ccc.clickagenda.dto.request.ServicoRequestDTO;
import jakarta.validation.Valid;
import br.iff.edu.ccc.clickagenda.dto.response.CategoriaResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ProfissionalResponseDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.CategoriaService;
import br.iff.edu.ccc.clickagenda.service.ProfissionalService;
import br.iff.edu.ccc.clickagenda.service.ServicoService;

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
    public String mostrarFormularioCadastro(Model model) {
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
        List<CategoriaResponseDTO> categorias = categoriaService.listarTodas();

        model.addAttribute("servico", new ServicoRequestDTO());
        model.addAttribute("profissionais", profissionais);
        model.addAttribute("categorias", categorias);

        return "servico-formulario";
    }

    @PostMapping
    public String salvarServico(@Valid ServicoRequestDTO servico, Model model) {
        try {
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
    public String mostrarPaginaSucesso(Model model) {
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
