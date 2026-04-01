package br.iff.edu.ccc.clickagenda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String nome;
    private String perfil;

    public LoginResponse(String token, Long id, String email, String nome, String perfil) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.perfil = perfil;
    }
}
