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
public class CategoriaDTO {
    private Long id;
    private String nome;
    private List<ServicoDTO> servicos;
}
