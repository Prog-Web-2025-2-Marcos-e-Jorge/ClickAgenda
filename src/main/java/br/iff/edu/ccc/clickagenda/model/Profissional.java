package br.iff.edu.ccc.clickagenda.model;

import java.util.List;

import br.iff.edu.ccc.clickagenda.enums.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profissional extends Usuario {

    @OneToMany(mappedBy = "profissional")
    private List<Servico> servicos;

    @ManyToMany
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "profissional")
    private List<Agendamento> agendamentos;

    @OneToMany(mappedBy = "profissional")
    private List<HorarioTrabalho> horariosTrabalho;

    public Profissional() {
        this.perfil = Perfil.PROFISSIONAL;
    }

    public Profissional(String nome, String cpf, String email, String telefone, String senha, String endereco) {
        super(nome, cpf, email, telefone, senha);
        this.perfil = Perfil.PROFISSIONAL;
    }
}
