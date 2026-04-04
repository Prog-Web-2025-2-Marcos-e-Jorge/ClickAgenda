package br.iff.edu.ccc.clickagenda.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iff.edu.ccc.clickagenda.dto.response.ProfissionalResponseDTO;
import br.iff.edu.ccc.clickagenda.service.ProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalViewController {

    private final ProfissionalService profissionalService;

    @GetMapping()
    public String getTodos(Model model) {
        List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();

        model.addAttribute("profissionais", profissionais);

        return "profissionais";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        ProfissionalResponseDTO profissional = profissionalService.buscarPorId(id);

        model.addAttribute("profissional", profissional);

        return "profissional-perfil";
    }

}
