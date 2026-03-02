package br.iff.edu.ccc.clickagenda.model;

import br.iff.edu.ccc.clickagenda.enums.Perfil;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Usuario {

    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        super(nome, cpf, email, telefone, senha);
        this.perfil = Perfil.CLIENTE;
    }
}
