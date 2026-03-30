package br.iff.edu.ccc.clickagenda.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.service.ServicoService;

@Controller
@RequestMapping("/servico")
public class ServicoViewController {

    @Autowired
    private ServicoService servicoService;

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
