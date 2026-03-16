package br.iff.edu.ccc.clickagenda.model;

import java.time.LocalTime;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HorarioTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Dia da semana não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @NotNull(message = "Horário de início não pode ser nulo")
    private LocalTime horarioInicio;

    @NotNull(message = "Horário de fim não pode ser nulo")
    private LocalTime horarioFim;

    private boolean diaFolga;

    @NotNull(message = "Profissional não pode ser nulo")
    @ManyToOne
    private Profissional profissional;
}
