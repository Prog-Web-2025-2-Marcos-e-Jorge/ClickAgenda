package br.iff.edu.ccc.clickagenda.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Usuario {

    private String cpf;

    public Cliente(String nome, String email, String telefone, String senha, String cpf) {
        super(nome, email, telefone, senha);
        this.cpf = cpf;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }
}
