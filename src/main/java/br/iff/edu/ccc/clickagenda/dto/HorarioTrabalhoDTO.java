package br.iff.edu.ccc.clickagenda.dto;

import java.time.LocalTime;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorarioTrabalhoDTO {
    private Long id;

    @NotNull(message = "Dia da semana é obrigatório")
    private DiaSemana diaSemana;

    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime horarioInicio;

    @NotNull(message = "Horário de fim é obrigatório")
    private LocalTime horarioFim;

    @NotNull(message = "Profissional é obrigatório")
    private ProfissionalDTO profissional;
}
