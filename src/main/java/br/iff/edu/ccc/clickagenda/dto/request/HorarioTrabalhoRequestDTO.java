package br.iff.edu.ccc.clickagenda.dto.request;

import java.time.LocalTime;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioTrabalhoRequestDTO {

    @NotNull(message = "Dia da semana é obrigatório")
    private DiaSemana diaSemana;

    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime horarioInicio;

    @NotNull(message = "Horário de fim é obrigatório")
    private LocalTime horarioFim;

    private boolean diaFolga;

    @NotNull(message = "Profissional é obrigatório")
    private Long profissionalId;
}
