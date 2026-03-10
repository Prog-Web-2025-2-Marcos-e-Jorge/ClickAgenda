package br.iff.edu.ccc.clickagenda.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer duracaoMinutos;
    private ProfissionalResponseDTO profissional;
    private CategoriaResponseDTO categoria;
}
