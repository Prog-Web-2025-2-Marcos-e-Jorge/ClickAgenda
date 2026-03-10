package br.iff.edu.ccc.clickagenda.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfissionalResponseDTO {
    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String telefone;

    private List<ServicoResponseDTO> servicos;

    private List<HorarioTrabalhoResponseDTO> horariosTrabalho;

    private List<CategoriaResponseDTO> categorias;
}
