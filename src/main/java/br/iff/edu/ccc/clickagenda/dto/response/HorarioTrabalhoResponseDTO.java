package br.iff.edu.ccc.clickagenda.dto.response;

import java.time.LocalTime;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioTrabalhoResponseDTO {
    private Long id;

    private DiaSemana diaSemana;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    private boolean diaFolga;

    private Long profissionalId;

    private String profissionalNome;
}
