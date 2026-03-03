package br.iff.edu.ccc.clickagenda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.iff.edu.ccc.clickagenda.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    private Profissional profissional;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Servico servico;
    private LocalDateTime dataHora;
    private String observacoes;
    private BigDecimal valor;
    private Status status;
    // PR da Camada de Persistência
}
