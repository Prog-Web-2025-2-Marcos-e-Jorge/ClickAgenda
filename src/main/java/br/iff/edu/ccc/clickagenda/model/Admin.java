package br.iff.edu.ccc.clickagenda.model;

import br.iff.edu.ccc.clickagenda.enums.Perfil;
import jakarta.persistence.Entity;

@Entity
public class Admin extends Usuario {

    public Admin() {
        this.perfil = Perfil.ADMIN;
    }

    public Admin(String nome, String cpf, String email, String telefone, String senha) {
        super(nome, cpf, email, telefone, senha);
        this.perfil = Perfil.ADMIN;
    }
}
