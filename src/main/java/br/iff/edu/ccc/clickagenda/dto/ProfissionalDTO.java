package br.iff.edu.ccc.clickagenda.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String email;
    private String telefone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @JsonIgnoreProperties("profissional")
    private List<ServicoDTO> servicos;

    @JsonIgnoreProperties("profissional")
    private List<HorarioTrabalhoDTO> horariosTrabalho;

    private List<CategoriaDTO> categorias;
}