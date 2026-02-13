package br.iff.edu.ccc.clickagenda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
