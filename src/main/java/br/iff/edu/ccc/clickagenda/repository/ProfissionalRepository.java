package br.iff.edu.ccc.clickagenda.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.iff.edu.ccc.clickagenda.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    Optional<Profissional> findByEmail(String email);
}
