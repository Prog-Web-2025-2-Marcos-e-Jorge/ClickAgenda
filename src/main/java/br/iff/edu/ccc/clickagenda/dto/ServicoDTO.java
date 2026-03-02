package br.iff.edu.ccc.clickagenda.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicoDTO {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer duracaoMinutos;
    private ProfissionalDTO profissional;
    private CategoriaDTO categoria;
}
