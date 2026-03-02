package br.iff.edu.ccc.clickagenda.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfissionalDTO {
    private Long id;
    private String nome;
    private String cpf;
    private List<ServicoDTO> servicos;
    private List<HorarioTrabalhoDTO> horariosTrabalho;
    private List<CategoriaDTO> categorias;
}
