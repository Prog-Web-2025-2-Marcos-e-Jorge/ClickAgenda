package br.iff.edu.ccc.clickagenda.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaResponseDTO {
    private Long id;
    private String nome;
    private List<ServicoResponseDTO> servicos;
}
