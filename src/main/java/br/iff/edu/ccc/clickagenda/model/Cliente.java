package br.iff.edu.ccc.clickagenda.model;

import java.util.List;

import br.iff.edu.ccc.clickagenda.enums.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente")
    private List<Agendamento> agendamentos;

    public Cliente() {
        this.perfil = Perfil.CLIENTE;
    }

    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        super(nome, cpf, email, telefone, senha);
        this.perfil = Perfil.CLIENTE;
    }

}
