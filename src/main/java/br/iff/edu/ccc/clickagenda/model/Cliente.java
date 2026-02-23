package br.iff.edu.ccc.clickagenda.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Usuario {

    private String cpf;

    public Cliente(String nome, String email, String telefone, String senha, String cpf) {
        super(nome, email, telefone, senha);
        this.cpf = cpf;
    }
}
