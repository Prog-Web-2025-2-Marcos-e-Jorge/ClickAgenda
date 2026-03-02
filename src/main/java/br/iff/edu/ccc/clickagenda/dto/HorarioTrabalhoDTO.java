package br.iff.edu.ccc.clickagenda.dto;

import java.time.LocalTime;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
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
    private DiaSemana diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private ProfissionalDTO profissional;
}
