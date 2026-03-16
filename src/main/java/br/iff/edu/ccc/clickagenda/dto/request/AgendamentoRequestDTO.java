package br.iff.edu.ccc.clickagenda.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoRequestDTO {
    @NotNull(message = "Profissional é obrigatório")
    private Long profissionalId;

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Serviço é obrigatório")
    private Long servicoId;

    @NotNull(message = "Data e horário são obrigatórios")
    @FutureOrPresent(message = "Data e horário devem ser no futuro ou presente")
    private LocalDateTime dataHora;

    @Size(max = 200, message = "Observações não podem exceder 200 caracteres")
    private String obs;

    @DecimalMin(value = "0.0", message = "Valor não pode ser negativo")
    private BigDecimal valor;
}
