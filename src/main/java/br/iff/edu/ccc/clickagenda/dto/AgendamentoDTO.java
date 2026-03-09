package br.iff.edu.ccc.clickagenda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.iff.edu.ccc.clickagenda.enums.Status;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "Profissional é obrigatório")
    private ProfissionalDTO profissional;

    @NotNull(message = "Cliente é obrigatório")
    private ClienteDTO cliente;

    @NotNull(message = "Serviço é obrigatório")
    private ServicoDTO servico;

    @NotNull(message = "Data/hora é obrigatória")
    @FutureOrPresent(message = "Data/hora deve ser no futuro ou presente")
    private LocalDateTime dataHora;

    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @DecimalMin(value = "0.0", message = "Valor não pode ser negativo")
    private BigDecimal valor;

    private Status status;
}
