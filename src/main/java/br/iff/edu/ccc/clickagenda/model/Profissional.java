package br.iff.edu.ccc.clickagenda.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profissional extends Usuario {
    private String cpf;
    @OneToMany(mappedBy = "profissional")
    private List<Servico> servicos;
    @OneToMany(mappedBy = "profissional")
    private List<Categoria> categorias;
    @OneToMany(mappedBy = "profissional")
    private List<HorarioTrabalho> horariosTrabalho;
    private String endereco;

    public Profissional(String nome, String email, String telefone, String senha, String cpf, String endereco) {
        super(nome, email, telefone, senha);
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }
}
