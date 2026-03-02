package br.iff.edu.ccc.clickagenda.model;

import java.util.List;

import br.iff.edu.ccc.clickagenda.enums.Perfil;
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
    @OneToMany(mappedBy = "profissional")
    private List<Servico> servicos;
    @OneToMany(mappedBy = "profissional")
    private List<Categoria> categorias;
    @OneToMany(mappedBy = "profissional")
    private List<HorarioTrabalho> horariosTrabalho;
    private String endereco;

    public Profissional(String nome, String cpf, String email, String telefone, String senha, String endereco) {
        super(nome, cpf, email, telefone, senha);
        this.endereco = endereco;
        this.perfil = Perfil.PROFISSIONAL;
    }
}
