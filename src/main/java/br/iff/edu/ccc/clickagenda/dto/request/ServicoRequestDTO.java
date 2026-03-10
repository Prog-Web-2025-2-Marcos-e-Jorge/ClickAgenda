package br.iff.edu.ccc.clickagenda.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoRequestDTO {

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.1", message = "Valor deve ser maior que 0")
    private BigDecimal valor;

    @NotNull(message = "Duração é obrigatória")
    @Min(value = 15, message = "Duração mínima é 15 minutos")
    private Integer duracaoMinutos;

    @NotNull(message = "Profissional é obrigatório")
    private Long profissionalId;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;
}
