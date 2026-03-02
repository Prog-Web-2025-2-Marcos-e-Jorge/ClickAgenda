package br.iff.edu.ccc.clickagenda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    private ProfissionalDTO profissional;
    private ClienteDTO cliente;
    private ServicoDTO servico;
    private LocalDateTime dataHora;
    private BigDecimal valor;
    private String observacoes;
}
