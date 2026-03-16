package br.iff.edu.ccc.clickagenda.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.iff.edu.ccc.clickagenda.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoResponseDTO {
    private Long id;
    private ProfissionalResponseDTO profissional;
    private ClienteResponseDTO cliente;
    private ServicoResponseDTO servico;
    private LocalDateTime dataHora;
    private String obs;
    private BigDecimal valor;
    private Status status;
}