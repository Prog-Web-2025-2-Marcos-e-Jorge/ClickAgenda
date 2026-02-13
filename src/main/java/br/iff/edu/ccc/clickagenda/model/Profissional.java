package br.iff.edu.ccc.clickagenda.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profissional extends Usuario {
    private String cpf;
    @OneToMany
    private List<Servico> servicos;
    @OneToMany
    private List<Categoria> categorias;
    @OneToMany
    private List<HorarioTrabalho> horariosTrabalho;
    private String endereco;

    public Profissional(String nome, String email, String telefone, String senha, String cpf, String endereco) {
        super(nome, email, telefone, senha);
        this.cpf = cpf;
        this.endereco = endereco;
    }
}
