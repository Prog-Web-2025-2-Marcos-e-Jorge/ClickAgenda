package br.iff.edu.ccc.clickagenda.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do serviço não pode ser vazio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Valor não pode ser nulo")
    @DecimalMin(value = "0.1", message = "Valor deve ser maior que 0")
    private BigDecimal valor;

    @NotNull(message = "Duração não pode ser nula")
    @Min(value = 15, message = "Duração mínima é 15 minutos")
    private Integer duracaoMinutos;

    @NotNull(message = "Profissional não pode ser nulo")
    @ManyToOne
    private Profissional profissional;

    @NotNull(message = "Categoria não pode ser nula")
    @ManyToOne
    private Categoria categoria;

    @OneToMany(mappedBy = "servico")
    private List<Agendamento> agendamentos;
}
