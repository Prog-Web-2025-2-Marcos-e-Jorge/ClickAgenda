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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HorarioTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    private boolean diaFolga;

    @ManyToOne
    private Profissional profissional;
}
