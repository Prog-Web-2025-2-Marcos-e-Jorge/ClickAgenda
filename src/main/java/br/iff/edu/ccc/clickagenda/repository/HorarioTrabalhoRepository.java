package br.iff.edu.ccc.clickagenda.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.iff.edu.ccc.clickagenda.enums.DiaSemana;
import br.iff.edu.ccc.clickagenda.model.HorarioTrabalho;

public interface HorarioTrabalhoRepository extends JpaRepository<HorarioTrabalho, Long> {
    Optional<HorarioTrabalho> findByProfissionalIdAndDiaSemana(Long profissionalId, DiaSemana diaSemana);
}