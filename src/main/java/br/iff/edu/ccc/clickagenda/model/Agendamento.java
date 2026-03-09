package br.iff.edu.ccc.clickagenda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.iff.edu.ccc.clickagenda.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Profissional não pode ser nulo")
    @ManyToOne
    private Profissional profissional;

    @NotNull(message = "Cliente não pode ser nulo")
    @ManyToOne
    private Cliente cliente;

    @NotNull(message = "Serviço não pode ser nulo")
    @ManyToOne
    private Servico servico;

    @NotNull(message = "Data/hora não pode ser nula")
    @FutureOrPresent(message = "Data/hora deve ser no futuro ou presente")
    private LocalDateTime dataHora;

    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @DecimalMin(value = "0.0", message = "Valor não pode ser negativo")
    private BigDecimal valor;

    private Status status;
}