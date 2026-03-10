package br.iff.edu.ccc.clickagenda.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}
