package br.iff.edu.ccc.clickagenda.model;

import org.hibernate.validator.constraints.br.CPF;

import br.iff.edu.ccc.clickagenda.enums.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
    protected String nome;

    @NotBlank(message = "CPF não pode ser vazio")
    @CPF
    protected String cpf;

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email inválido")
    protected String email;

    @NotBlank(message = "Telefone não pode ser vazio")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    protected String telefone;

    @NotBlank(message = "Senha não pode ser vazia")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    protected String senha;

    @NotNull(message = "Perfil não pode ser nulo")
    protected Perfil perfil;

    protected boolean ativo = true;

    protected Usuario(String nome, String cpf, String email, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
}
